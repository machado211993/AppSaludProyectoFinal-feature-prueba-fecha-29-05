package com.serviciosalud.demo.repositorios;

import com.serviciosalud.demo.entidades.Paciente;
import com.serviciosalud.demo.entidades.Profesional;
import com.serviciosalud.demo.entidades.Usuario;
import com.serviciosalud.demo.enumeraciones.Especialidad;
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
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.id= :id")
    public Paciente buscarUsuarioPorId(@Param("id") String id);

    @Query("SELECT u FROM Usuario u WHERE u.dni= :dni")
    public Paciente buscarUsuarioPorDni(@Param("dni") Integer dni);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarUsuarioPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Paciente buscarPacientePorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.matricula= :matricula")
    public Profesional buscarUsuarioPorMatricula(@Param("matricula") Long matricula);

    @Query("SELECT u FROM Usuario u WHERE u.especialidad = :especialidad")
    public List<Profesional> buscarPorEspecialidad(@Param("especialidad") Especialidad especialidad);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'PROFESIONAL' ORDER BY u.precio ASC")
    public List<Profesional> ordenarPorPrecio();

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'PROFESIONAL' ORDER BY u.calificacion DESC")
    public List<Profesional> ordenarPorCalificacion();

    @Query("SELECT u FROM Usuario u WHERE u.nombreEstablecimiento= :nombreEstablecimiento")
    public Profesional buscarUsuarioPorEstablecimiento(@Param("nombreEstablecimiento") String nombreEstablecimiento);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Profesional buscarProfesionalPorEmail(@Param("email") String email);

}
