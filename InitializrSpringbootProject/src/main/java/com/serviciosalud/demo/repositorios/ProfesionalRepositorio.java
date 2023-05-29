
package com.serviciosalud.demo.repositorios;

import com.serviciosalud.demo.entidades.Profesional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Samu Noah
 */
@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {
    
  /*  @Query("SELECT p FROM Profesional p WHERE p.id= :id")
    public Profesional buscarProfesionalPorId(@Param("id") String id);
    
     @Query("SELECT p FROM Profesional p WHERE p.dni= :dni")
    public Profesional buscarProfesionalPorDni(@Param("dni") Long dni);
    
    @Query("SELECT p FROM Profesional p WHERE p.matricula= :matricula")
    public Profesional buscarProfesionalPorMatricula(@Param("matricula") Long matricula);
    
    @Query("SELECT p FROM Profesional p WHERE p.especialidad= :especialidad")
    public List<Profesional> buscarPorEspecialidad (@Param("especialidad") String especialidad);*/
    
}
