package com.serviciosalud.demo;

/*import com.serviciosalud.demo.servicios.PacienteServicio;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppSaludProyectoFinalApplication {

    public static void main(String[] args) {

     /*   PacienteServicio pacienteServicio = new PacienteServicio();
            pacienteServicio.crearUsuariolisto();*/
        SpringApplication.run(AppSaludProyectoFinalApplication.class, args);

    }

}
