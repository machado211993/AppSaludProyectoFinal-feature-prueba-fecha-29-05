package com.serviciosalud.demo.entidades;

import com.serviciosalud.demo.enumeraciones.Estado;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Turno {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    
    protected Estado estado;
    protected String fecha;
    protected String horario;
    protected String motivoDeConsulta;
    
    @ManyToOne
    protected Paciente paciente;
    
    @ManyToOne
    protected Profesional profesional;

}
