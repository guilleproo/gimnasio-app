package com.ejemplo.gimnasio.service;

import com.ejemplo.gimnasio.model.Usuario;
import com.ejemplo.gimnasio.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service 
public class UsuarioService {
    
    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder passwordEncoder){
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // LISTAR USUARIOS
    public List<Usuario> listar(String q){
        if (q != null && !q.isBlank()) {
            return repo.findByNombreContainingIgnoreCaseOrUsernameContainingIgnoreCase(q, q);
        }
        return repo.findAll();
    }

    // Listar 1 usuario
    public Usuario buscarPorId(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
    }

    // CREAR USUARIOS 
    @Transactional 
    public Usuario crear(Usuario usuario){
        if (repo.existsByUsername(usuario.getUsername())){
            throw new IllegalArgumentException("Este usuario ya existe!");
        }
        
        if (repo.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        if(usuario.getPassword() == null || usuario.getPassword().isBlank()){
            throw new IllegalArgumentException("La contrasena es obligatoria!");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repo.save(usuario);
    }

    // ACTUALIZAR USUARIOS 
    @Transactional 
    public Usuario actualizar(Long id, Usuario datos){
        Usuario existente = buscarPorId(id);

        if(!existente.getUsername().equals(datos.getUsername())
            && repo.existsByUsername(datos.getUsername())){
                throw new IllegalArgumentException("El username ya existe!");
            }

        if(!existente.getEmail().equals(datos.getEmail())
            && repo.existsByEmail(datos.getEmail())){
                throw new IllegalArgumentException("El correo ya está registrado");
            }

        existente.setNombre(datos.getNombre());
        existente.setUsername(datos.getUsername());
        existente.setEmail(datos.getEmail());
        existente.setRol(datos.getRol());
        
        existente.setTelefono(datos.getTelefono());
        existente.setActivo(datos.isActivo());

        if (datos.getPassword() != null && !datos.getPassword().isBlank()){
            existente.setPassword(passwordEncoder.encode(datos.getPassword()));
        }

        return repo.save(existente);
    }

    // ELIMINAR 
    @Transactional 
    public void eliminar(Long id){
        repo.deleteById(id);
    }

}