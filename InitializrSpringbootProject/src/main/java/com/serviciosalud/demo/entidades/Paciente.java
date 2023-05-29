
package com.serviciosalud.demo.entidades;


import javax.persistence.Entity;
import lombok.Data;

/**
 *
 * @author Samu Noah
 */

@Entity
@Data
public class Paciente extends Usuario  {
    
    private String obraSocialPaciente;
    private Integer numeroDeAfiliado;
    
    
}
