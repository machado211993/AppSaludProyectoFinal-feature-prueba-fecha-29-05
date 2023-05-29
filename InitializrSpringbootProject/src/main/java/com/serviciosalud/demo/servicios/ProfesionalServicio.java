package com.serviciosalud.demo.servicios;

import com.serviciosalud.demo.MiExcepcion.MiExcepcion;
import com.serviciosalud.demo.entidades.Imagen;
import com.serviciosalud.demo.entidades.Profesional;
import com.serviciosalud.demo.entidades.Usuario;
import com.serviciosalud.demo.enumeraciones.Especialidad;
import com.serviciosalud.demo.enumeraciones.Modalidad;
import com.serviciosalud.demo.enumeraciones.Roles;
import com.serviciosalud.demo.repositorios.ProfesionalRepositorio;
import com.serviciosalud.demo.repositorios.UsuarioRepositorio;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfesionalServicio implements UserDetailsService {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    /*metodo para registrar profesional*/
    @Transactional
    public void registrar(MultipartFile archivo, Date fecha, String nombre, String apellido, Integer dni, String email, Integer telefono, String sexo,
            String password, String password2, Long matricula, String especialidad, String modalidad, Double precio,
            String inicioDia, String finDia, String inicioHora, String finHora, Double calificacion, String localidad,
            String obraSocial, Long telefonoLaboral, String descripcion, String nombreEstablecimiento) throws MiExcepcion {

        validar(nombre, apellido, dni, email, telefono,
                sexo, password, password2, matricula, especialidad, precio, calificacion, localidad,
                obraSocial, telefonoLaboral, descripcion, nombreEstablecimiento);
        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setDni(dni);
        profesional.setEmail(email);
        profesional.setTelefono(telefono);
        profesional.setSexo(sexo);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        /* profesional.setPassword(password);*/

        profesional.setRol(Roles.PROFESIONAL);

        profesional.setActivo(true);
        profesional.setMatricula(matricula);

        for (Especialidad x : Especialidad.values()) {
            if (especialidad.equals(x.toString())) {
                profesional.setEspecialidad(x);
            }
        }

        for (Modalidad x : Modalidad.values()) {
            if (modalidad.equals(x.toString())) {
                profesional.setModalidad(x);
            }
        }

        //registrarDisponibilidadDias(profesional, inicioDia, finDia);
        profesional.setDisponibilidadInicioDia(inicioDia);
        profesional.setDisponibilidadFinDia(finDia);
        profesional.setDisponibilidadInicioHora(inicioHora);
        profesional.setDisponibilidadFinHora(finHora);
        profesional.setPrecio(precio);
        profesional.setCalificacion(calificacion);
        profesional.setLocalidad(localidad);
        profesional.setObraSocial(obraSocial);
        profesional.setTelefonoLaboral(telefonoLaboral);
        profesional.setDescripcion(descripcion);
        profesional.setNombreEstablecimiento(nombreEstablecimiento);

        profesional.setFechaDeNacimiento(fecha);
        Imagen imagen = imagenServicio.guardar(archivo);
        profesional.setImg(imagen);
        profesionalRepositorio.save(profesional);
        System.out.println("save" + profesional.getEmail());
    }

    @Transactional
    public void actualizarProfesional(MultipartFile archivo, Date fecha, String idProfesional, String nombre, String apellido, Integer dni, String email, Integer telefono,
            String sexo, String password, String password2, Long matricula, String especialidad, String modalidad, Double precio,
            String inicioDia, String finDia, String inicioHora, String finHora, Double calificacion, String localidad,
            String obraSocial, Long telefonoLaboral, String descripcion, String nombreEstablecimiento, Boolean activo) throws MiExcepcion {

        validar(nombre, apellido, dni, email, telefono,
                sexo, password, password2, matricula, especialidad, precio, calificacion, localidad,
                obraSocial, telefonoLaboral, descripcion, nombreEstablecimiento);

        Optional<Profesional> respuesta = profesionalRepositorio.findById(idProfesional);

        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setDni(dni);
            profesional.setEmail(email);
            profesional.setTelefono(telefono);
            profesional.setSexo(sexo);
            /* usuario.setPassword(new BCryptPasswordEncoder().encode(password));*/
            profesional.setPassword(new BCryptPasswordEncoder().encode(password));

            profesional.setRol(Roles.PROFESIONAL);

            profesional.setActivo(activo);
            profesional.setMatricula(matricula);
            profesional.setFechaDeNacimiento(fecha);
            for (Especialidad x : Especialidad.values()) {
                if (especialidad.equals(x.toString())) {
                    profesional.setEspecialidad(x);
                }
            }
            for (Modalidad x : Modalidad.values()) {
                if (modalidad.equals(x.toString())) {
                    profesional.setModalidad(x);
                }
            }
            profesional.setDisponibilidadInicioDia(inicioDia);
            profesional.setDisponibilidadFinDia(finDia);
            profesional.setDisponibilidadInicioHora(inicioHora);
            profesional.setDisponibilidadFinHora(finHora);
            profesional.setPrecio(precio);
            profesional.setCalificacion(calificacion);
            profesional.setLocalidad(localidad);
            profesional.setObraSocial(obraSocial);
            profesional.setTelefonoLaboral(telefonoLaboral);
            profesional.setDescripcion(descripcion);
            profesional.setNombreEstablecimiento(nombreEstablecimiento);

            String idImagen = null;
            if (profesional.getImg() != null) {
                idImagen = profesional.getImg().getId();
            }
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            profesional.setImg(imagen);

            profesionalRepositorio.save(profesional);

        }
    }

    public void registrarDisponibilidadDias(Profesional profesional, String inicioDia, String finDia) {

        switch (inicioDia) {
            case "monday":
                profesional.setDisponibilidadInicioDia("lunes-");
                break;
            case "tuesday":
                profesional.setDisponibilidadInicioDia("martes-");
                break;
            case "wednesday":
                profesional.setDisponibilidadInicioDia("miercoles-");
                break;
            case "thursday":
                profesional.setDisponibilidadInicioDia("jueves-");
                break;
            case "friday":
                profesional.setDisponibilidadInicioDia("viernes-");
                break;
            case "saturday":
                profesional.setDisponibilidadInicioDia("viernes-");
                break;
            case "sunday":
                profesional.setDisponibilidadInicioDia("viernes-");
                break;
        }

        switch (finDia) {
            case "monday":
                //profesional.setDisponibilidadDias(profesional.getDisponibilidadDias()+"lunes");
                break;
            case "tuesday":
                profesional.setDisponibilidadFinDia("martes");
                break;
            case "wednesday":
                profesional.setDisponibilidadFinDia("miercoles");
                break;
            case "thursday":
                profesional.setDisponibilidadFinDia("jueves");
                break;
            case "friday":
                profesional.setDisponibilidadFinDia("viernes");
                break;
            case "saturday":
                profesional.setDisponibilidadFinDia("sabado");
                break;
            case "sunday":
                profesional.setDisponibilidadFinDia("domindo");
                break;
        }

    }

    @Transactional
    public void borrarPorId(String id) {
        profesionalRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Profesional getOne(String id) {
        return profesionalRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public Profesional buscarPorMatricula(Long matricula) {
        return usuarioRepositorio.buscarUsuarioPorMatricula(matricula);
    }

    @Transactional(readOnly = true)
    public List<Profesional> buscarPorEspecialidad(Especialidad especialidad) {
        return usuarioRepositorio.buscarPorEspecialidad(especialidad);
    }
    
    @Transactional(readOnly = true)
    public List<Profesional> ordenarPorPrecio() {
        return usuarioRepositorio.ordenarPorPrecio();
    }
    
    @Transactional(readOnly = true)
    public List<Profesional> ordenarPorCalificacion() {
        return usuarioRepositorio.ordenarPorCalificacion();
    }

    @Transactional
    public List<Profesional> listaProfesinales() {

        return profesionalRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Profesional buscarPorEmail(String email) {
        return usuarioRepositorio.buscarProfesionalPorEmail(email);
    }

    @Transactional(readOnly = true)
    public Profesional buscarPorNombreEstablecimiento(String nombreEstablecimiento) {
        return usuarioRepositorio.buscarUsuarioPorEstablecimiento(nombreEstablecimiento);
    }


    /*metodo de validacion*/
    private void validar(String nombre, String apellido, Integer dni, String email,
            Integer telefono, String sexo, String password, String password2, Long matricula, String especialidad, Double precio, Double calificacion, String localidad,
            String obraSocial, Long telefonoLaboral, String descripcion, String nombreEstablecimiento) throws MiExcepcion {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("el nombre del usuario no puede ser nulo ni estar vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("el apellido del usuario no puede ser nulo ni estar vacío");
        }
        if (dni == null || dni <= 0) {
            throw new MiExcepcion("el DNI del usuario no puede ser nulo ni ser menor o igual a 0");
        }
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("el email no puede ser nulo ni estar vacío");
        }
        if (localidad == null || localidad.isEmpty()) {
            throw new MiExcepcion("la localidad no puede ser nulo ni estar vacío");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new MiExcepcion("la descripcion no puede ser nulo ni estar vacío");
        }
        if (obraSocial == null || obraSocial.isEmpty()) {
            throw new MiExcepcion("la obra social no puede ser nulo ni estar vacío");
        }
        if (nombreEstablecimiento == null || nombreEstablecimiento.isEmpty()) {
            throw new MiExcepcion("el nombre del establecimiento no puede ser nulo ni estar vacío");
        }
        if (sexo == null || sexo.isEmpty()) {
            throw new MiExcepcion("el sexo no puede ser nulo ni estar vacío");
        }
        if (matricula == null || matricula <= 0) {
            throw new MiExcepcion("la matricula no puede ser nulo ni ser menor o igual a 0");
        }
        if (precio == null || precio <= 0) {
            throw new MiExcepcion("el precio no puede ser nulo ni ser menor o igual a 0");
        }
        if (calificacion == null || calificacion <= 0) {
            throw new MiExcepcion("la calificafion no puede ser nulo ni ser menor o igual a 0");
        }
        if (telefonoLaboral == null || telefonoLaboral <= 0) {
            throw new MiExcepcion("el telefono laboral no puede ser nulo ni ser menor o igual a 0");
        }
        if (telefono == null || telefono <= 0) {
            throw new MiExcepcion("el telefono del usuario no puede ser nulo ni ser menor o igual a 0");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiExcepcion("el password del usuario no puede estar vacío y debe tener más de 6 digitos");
        }
        if (!password2.equals(password)) {
            throw new MiExcepcion("los passwords ingresados deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("1" + email);

        Usuario profesional = usuarioRepositorio.buscarUsuarioPorEmail(email);

        System.out.println("4." + profesional.getEmail());

        if (profesional != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + profesional.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", profesional);

            return new User(profesional.getEmail(), profesional.getPassword(), permisos);

        } else {
            return null;
        }
    }

}
