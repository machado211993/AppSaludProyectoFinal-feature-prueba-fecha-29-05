package com.serviciosalud.demo.entidades;

import com.serviciosalud.demo.enumeraciones.Especialidad;
import com.serviciosalud.demo.enumeraciones.Modalidad;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;

@Entity
@Data
public class Profesional extends Usuario{
        
    private Long matricula;
    
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;
    
    private Double precio;
    private Double calificacion;
    private String localidad;
    private String obraSocial;
    private Long telefonoLaboral;
    private String descripcion;
    private String nombreEstablecimiento;
    
    private String disponibilidadInicioDia;
    private String disponibilidadFinDia;
    private String disponibilidadInicioHora;
    private String disponibilidadFinHora;
    
    private Boolean activo;
    
    /*falta crear las fecha y turno horarios*/
    
    
    
}
