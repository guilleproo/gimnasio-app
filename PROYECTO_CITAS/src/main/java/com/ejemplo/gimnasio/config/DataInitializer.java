package com.ejemplo.gimnasio.config;

import com.ejemplo.gimnasio.model.Usuario;
import com.ejemplo.gimnasio.repository.UsuarioRepository;
import com.ejemplo.gimnasio.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class DataInitializer {
    
    @Bean 
    CommandLineRunner cargarUsuarios(UsuarioRepository repo, UsuarioService service){
        return args -> {
            if (repo.count() == 0 ){
                // Agrego el campo de telefono
                service.crear(new Usuario("Guillermo Entrenador", "guille", "guillepro@gimnasio.com", "admin123", "ENTRENADOR", "88888888"));
                service.crear(new Usuario("Maria Cliente", "maria", "maria1@gimnasio.com", "maria123", "CLIENTE", "77777777"));
            }
        };
    }
}