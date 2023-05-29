package com.serviciosalud.demo.controladores;

import com.serviciosalud.demo.MiExcepcion.MiExcepcion;
import com.serviciosalud.demo.servicios.ProfesionalServicio;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    public ProfesionalServicio profesionalServicio;

    @GetMapping("/dashboard")
    public String panelAdminstrativo() {
        return "panel.html";
    }

    @GetMapping("/registrar-profesional")
    public String registrarProfesional() {
        return "registro_profesional.html";
    }

    @PostMapping("/registro-profesional")
    public String registroProfesional(MultipartFile archivo, @RequestParam @DateTimeFormat(pattern= "yyyy-MM-dd")Date fecha,@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam(required = false) Integer dni, @RequestParam String email, @RequestParam(required = false) Integer telefono,
            @RequestParam String sexo, @RequestParam String password, @RequestParam String password2, @RequestParam Long matricula,
            @RequestParam String especialidad, @RequestParam String modalidad, @RequestParam Double precio, @RequestParam String inicioDia, @RequestParam String finDia,
            @RequestParam String inicioHora, @RequestParam String finHora, @RequestParam Double calificacion, @RequestParam String localidad, 
            @RequestParam String obraSocial,@RequestParam Long telefonoLaboral, @RequestParam String descripcion,
            @RequestParam String nombreEstablecimiento, ModelMap modelo) {

        
        try {
            profesionalServicio.registrar(archivo,fecha,nombre, apellido, dni, email, telefono, sexo, password, password2,
                    matricula, especialidad, modalidad, precio, inicioDia, finDia, inicioHora, finHora, calificacion, localidad, obraSocial, 
                    telefonoLaboral, descripcion, nombreEstablecimiento);

            modelo.put("exito", "Usted se ha registrado correctamete");

        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "registro_profesional.html";

        }

        return "panel.html";
    }

}
