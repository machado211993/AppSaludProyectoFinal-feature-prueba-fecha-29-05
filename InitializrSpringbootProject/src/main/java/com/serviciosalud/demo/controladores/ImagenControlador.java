package com.serviciosalud.demo.controladores;

import com.serviciosalud.demo.entidades.Usuario;
import com.serviciosalud.demo.servicios.PacienteServicio;
import com.serviciosalud.demo.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    PacienteServicio pacienteServicio;

    @Autowired
    ProfesionalServicio profesionalServicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {

        Usuario usuario = pacienteServicio.getOne(id);

        byte[] imagen =  usuario.getImg().getContenido();
        
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG); //se va a recibir una imagen de tipo JPEG
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/pacientes/{id}")
    public ResponseEntity<byte[]> imagenPaciente(@PathVariable String id) {

        Usuario usuario = pacienteServicio.getOne(id);

        byte[] imagen =  usuario.getImg().getContenido();
        
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG); //se va a recibir una imagen de tipo JPEG
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/profesional/{id}")
    public ResponseEntity<byte[]> imagenProfesional(@PathVariable String id) {

        Usuario usuario = profesionalServicio.getOne(id);

        byte[] imagen =  usuario.getImg().getContenido();
        
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG); //se va a recibir una imagen de tipo JPEG
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

}