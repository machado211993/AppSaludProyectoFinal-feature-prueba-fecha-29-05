package com.serviciosalud.demo.controladores;

import com.serviciosalud.demo.MiExcepcion.MiExcepcion;
import com.serviciosalud.demo.entidades.Paciente;
import com.serviciosalud.demo.entidades.Turno;
import com.serviciosalud.demo.entidades.Usuario;
import com.serviciosalud.demo.repositorios.TurnoRepositorio;
import com.serviciosalud.demo.servicios.PacienteServicio;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Samu Noah
 */
@Controller
@RequestMapping("/paciente")
public class PacienteResControler {

    @Autowired
    PacienteServicio pacienteServicio;

    @Autowired
    TurnoRepositorio turnoRepositorio;

    @GetMapping("/registro")
    public String registrar() {

        return "/registro.html";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(MultipartFile archivo, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam(required = false) Integer dni, @RequestParam String email, @RequestParam(required = false) Integer telefono,
            @RequestParam String sexo, @RequestParam String password, @RequestParam String password2,
            @RequestParam String obraSocialPaciente, @RequestParam Integer numeroDeAfiliado, 
            ModelMap modelo) throws MiExcepcion, ParseException {

        try {
            pacienteServicio.registrar(archivo, fecha, nombre, apellido, dni, email, telefono, sexo, password, password2, obraSocialPaciente,
                    numeroDeAfiliado);

            modelo.put("exito", "Usted se ha registrado correctamete");

        } catch (MiExcepcion ex) {

            modelo.put("error", ex.getMessage());
            return "registro.html";

        }
        return "index.html";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        modelo.put("usuario", usuario);

        List<Paciente> pacientes = pacienteServicio.listaPacientes();

        /* modelo.addAttribute("pacientes", pacientes);*/
        modelo.put("pacientes", pacientes);

        return "listar_pacientes.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("paciente", pacienteServicio.getOne(id));

        return "modificar_paciente.html";
    }

    @PostMapping("/modificado/{idPaciente}")
    public String modificarUsuario(@PathVariable String idPaciente, MultipartFile archivo, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam(required = false) Integer dni, @RequestParam String email, @RequestParam(required = false) Integer telefono,
            @RequestParam String sexo, @RequestParam String password, @RequestParam String password2,
            @RequestParam String obraSocialPaciente, @RequestParam Integer numeroDeAfiliado,
            ModelMap modelo) throws MiExcepcion, ParseException {

        try {

            pacienteServicio.actualizar(archivo, fecha, idPaciente, nombre, apellido, dni, email, telefono, sexo, password, password2, obraSocialPaciente, numeroDeAfiliado);
            modelo.put("exito", "Usted se ha actualizado correctamete");
            return "index.html";
        } catch (MiExcepcion ex) {

            modelo.put("error", ex.getMessage());
            return "modificar_paciente.html";

        }
    }

    @GetMapping("/turnos/{id}")
    public String listarMisTurno(@PathVariable String id, ModelMap modelo) {
        List<Turno> misTurnos;
        misTurnos = turnoRepositorio.buscarPorPaciente(id);
        modelo.addAttribute("turnos", misTurnos);
        return "listar_turnos.html";
    }
    
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        modelo.put("paciente", pacienteServicio.getOne(id));
        return "eliminar_paciente.html";
    }

    @PostMapping("/eliminado/{id}")
    public String eliminado(@PathVariable String id, String nombre, ModelMap modelo) {

        pacienteServicio.borrarPorId(id);

        return "inicio.html";
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteServicio.listaPacientes());
    }

    @GetMapping("/pacientes2")
    public String listaPacientes() {
        pacienteServicio.crearUsuariolisto();
        return "index.html";
    }

}
