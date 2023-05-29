package com.serviciosalud.demo.entidades;

import com.serviciosalud.demo.enumeraciones.Roles;
import java.util.Date;
import javax.persistence.Entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Data
/*@MappedSuperclass*/
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    protected Integer dni;
    protected String nombre;
    protected String apellido;
    protected Integer telefono;
    protected String email;
    protected String password;

    @Temporal(TemporalType.DATE)
    protected Date fechaDeNacimiento;

    @OneToOne
    protected Imagen img;

    @Enumerated(EnumType.STRING)
    protected Roles rol;

    protected String sexo;

}
