package com.serviciosalud.demo.controladores;

import com.serviciosalud.demo.MiExcepcion.MiExcepcion;
import com.serviciosalud.demo.entidades.Usuario;
import com.serviciosalud.demo.entidades.Turno;
import com.serviciosalud.demo.entidades.Profesional;
import com.serviciosalud.demo.repositorios.PacienteRepositorio;
import com.serviciosalud.demo.repositorios.TurnoRepositorio;
import com.serviciosalud.demo.servicios.PacienteServicio;
import com.serviciosalud.demo.servicios.ProfesionalServicio;
import com.serviciosalud.demo.servicios.TurnoServicio;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    ProfesionalServicio profesionalServicio;

    @Autowired
    TurnoServicio turnoServicio;

    @Autowired
    TurnoRepositorio turnoRepositorio;

    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @GetMapping("/registrar/{idProfesional}")
    public String registrarTurno(@PathVariable String idProfesional, ModelMap modelo, HttpSession session) {

        Profesional profesional = profesionalServicio.getOne(idProfesional);

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        System.out.println(">>" + logueado.getNombre());

        modelo.put("profesional", profesional);

        List<String> horas = listaHoras(profesional.getDisponibilidadInicioHora(), profesional.getDisponibilidadFinHora());
        modelo.put("horas", horas);

        List<String> dias = listaDias(profesional);
        modelo.put("dias", dias);

        Month[] meses = Month.values();
        modelo.put("meses", meses);

        return "registrar_turno.html";
    }

    public List<String> listaHoras(String inicioHora, String finHora) {
        List<String> listaHoras = new ArrayList<>();

        LocalTime horaInicioComparar = LocalTime.parse(inicioHora, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime horaFinComparar = LocalTime.parse(finHora, DateTimeFormatter.ofPattern("HH:mm"));

        LocalTime horaActual = horaInicioComparar;

        while (horaActual.isBefore(horaFinComparar) || horaActual.equals(horaFinComparar)) {
            System.out.println(horaActual.format(DateTimeFormatter.ofPattern("HH:mm")));
            listaHoras.add(horaActual.format(DateTimeFormatter.ofPattern("HH:mm")));
            horaActual = horaActual.plusMinutes(30);
        }
        return listaHoras;
    }

    public List<String> listaDias(Profesional profesional) {
        boolean contador = false; //auxiliar para que 

        List<String> lista = new ArrayList<>(); // lista para guardar los dias y despues setear al atributo List<String> disponibilidadDia

        DayOfWeek dia = DayOfWeek.SUNDAY; // el primer dia a comparar

        DayOfWeek diaInicioComparar = DayOfWeek.valueOf(profesional.getDisponibilidadInicioDia().toUpperCase()); // transformo en inicioDia a DayOfWeek para poder comparar
        DayOfWeek diaFinComparar = DayOfWeek.valueOf(profesional.getDisponibilidadFinDia().toUpperCase()); // transformo en finDia a DayOfWeek para poder comparar

        while (dia != diaInicioComparar && contador == false) {
            dia = dia.plus(1);  // mientras dia != de diaInicio dia ira cambiando al siguiente dia

            if (dia.equals(diaInicioComparar)) { //cuando dia sea igual a inicioDia

                for (int i = 0; i < 6; i++) { //for 7 veces max
                    if (dia != diaFinComparar) { // mientras dia no llegue a diaFin

                        lista.add(dia.toString()); // va agregando los dias a la lista

                        dia = dia.plus(1);// dia ira cambiando de uno en uno
                    } else {
                        lista.add(dia.toString()); // agrega el ultima dia que quedo fuera del primer if()

                        contador = true;  // condicion linea100 para que frene el while
                        break; //sale del for, aunque no haya llegado a la max de 7 vueltas
                    }
                }

            }
        }

        for (String x : lista) {
            System.out.println("dias>" + x.toString()); //muestra en el output los diasDisponibles ">>..."
        }
        return lista;
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String idProfesional, @RequestParam String idPaciente, @RequestParam String mes,
            @RequestParam String dia, @RequestParam String hora, @RequestParam String motivoConsulta, ModelMap modelo, @RequestParam(required = false) String error) {

        try {
            turnoServicio.registrar(idPaciente, idProfesional, mes, dia, hora, motivoConsulta);

            return "inicio.html";
        } catch (MiExcepcion ex) {
            Profesional profesional = profesionalServicio.getOne(idProfesional);

            modelo.put("profesional", profesional);

            List<String> horas = listaHoras(profesional.getDisponibilidadInicioHora(), profesional.getDisponibilidadFinHora());
            modelo.put("horas", horas);

            List<String> dias = listaDias(profesional);
            modelo.put("dias", dias);

            Month[] meses = Month.values();
            modelo.put("meses", meses);

            modelo.put("error", "Esa fecha ya está reservada con el mismo profesional a esa misma hora!!!!");

            modelo.put("idProfesional", idProfesional);
            modelo.put("idPaciente", idPaciente);

            return "registrar_turno.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        Optional<Turno> respuesta = turnoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();
            
            LocalDate fecha = LocalDate.parse(turno.getFecha());

            modelo.put("turno", turno);
            
            
            modelo.put("mesGuardado", fecha.getMonth());
            System.out.println("TCONT: mes: " + fecha.getMonth().toString());
            modelo.put(("diaGuardado"), fecha.getDayOfWeek().toString());
            System.out.println("TCONT: dia: " + fecha.getDayOfWeek());
            modelo.put("horaGuardada", turno.getHorario());
            System.out.println("TCONT: hora: " + turno.getHorario());

            List<String> horas = listaHoras(turno.getProfesional().getDisponibilidadInicioHora(), turno.getProfesional().getDisponibilidadFinHora());

            modelo.put("horas", horas);

            List<String> dias = listaDias(turno.getProfesional());
            modelo.put("dias", dias);

            Month[] meses = Month.values();
            modelo.put("meses", meses);
        }

        return "modificar_turno.html";
    }

    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable String id, @RequestParam String idProfesional, @RequestParam String idPaciente, @RequestParam String mes,
            @RequestParam String dia, @RequestParam String hora, @RequestParam("motivoConsulta") String motivoConsulta, ModelMap modelo) {

        try {
            turnoServicio.modificar(id, idPaciente, idProfesional, mes, dia, hora, motivoConsulta);

            return "inicio.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "modificar_turno.html";

        }
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        
        List<Turno> turnos = turnoServicio.listarTurnos();
        
        modelo.addAttribute("turnos", turnos);
        
        return "listar_turnos.html";
    }
    

    @GetMapping("/cancelar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        
        modelo.put("turno", turnoRepositorio.getOne(id));
        
        return "eliminar_turno.html";
    }
    
    @PostMapping("/cancelado/{id}")
    public String eliminado(@PathVariable String id){
        
        turnoRepositorio.deleteById(id);
        return "inicio.html";
    }
}
