package com.ejemplo.gimnasio.repository;

import com.ejemplo.gimnasio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; 
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    // metodo para verificar correo
    boolean existsByEmail(String email);

    // metodo busqueda 
    List<Usuario> findByNombreContainingIgnoreCaseOrUsernameContainingIgnoreCase(String nombre, String username);
}