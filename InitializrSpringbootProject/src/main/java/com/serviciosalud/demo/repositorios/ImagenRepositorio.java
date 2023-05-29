
package com.serviciosalud.demo.repositorios;

import com.serviciosalud.demo.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Samu Noah
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    
}

