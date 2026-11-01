package com.ejemplo.gimnasio.service;

import com.ejemplo.gimnasio.model.Clase;
import com.ejemplo.gimnasio.model.Usuario;
import com.ejemplo.gimnasio.repository.ClaseRepository;
import com.ejemplo.gimnasio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaseService {

    private final ClaseRepository claseRepository;
    private final UsuarioRepository usuarioRepository;

    public ClaseService(ClaseRepository claseRepository, UsuarioRepository usuarioRepository) {
        this.claseRepository = claseRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Clase> listarTodas() {
        return claseRepository.findAll();
    }

    public void guardar(Clase clase) {
        // Validar que el usuario asignado sea realmente un ENTRENADOR
        if (clase.getEntrenador() != null && clase.getEntrenador().getId() != null) {
            Usuario entrenador = usuarioRepository.findById(clase.getEntrenador().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Entrenador no encontrado"));
            
            if (!"ENTRENADOR".equals(entrenador.getRol())) {
                throw new IllegalArgumentException("El usuario seleccionado no tiene el rol de Entrenador.");
            }
            clase.setEntrenador(entrenador);
        }
        
        claseRepository.save(clase);
    }

    public Clase buscarPorId(Long id) {
        return claseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La clase solicitada no existe."));
    }

    public void eliminar(Long id) {
        claseRepository.deleteById(id);
    }
}