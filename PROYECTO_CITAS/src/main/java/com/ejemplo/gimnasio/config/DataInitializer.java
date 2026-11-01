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
                service.crear(new Usuario("Administrador", "admin", "admin@admin.com", "admin123", "ADMIN", "88888888"));
                service.crear(new Usuario("Maria", "maria", "maria@ejemplo.com", "maria123", "USER", "77777777"));
            }
        };
    }
}