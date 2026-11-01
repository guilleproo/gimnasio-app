package com.ejemplo.gimnasio.controller;

import com.ejemplo.gimnasio.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Control de login y recuperacion de password

@Controller 
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/usuarios";
    }

    // --- recuperar password ---

    // 1. mostrar la vista para recuperar password
    @GetMapping("/recuperar")
    public String mostrarFormularioRecuperar() {
        return "usuarios/recuperar"; 
    }

    // 2. Procesar los datos nuevos
    @PostMapping("/recuperar")
    public String procesarRecuperacion(@RequestParam String username, 
                                       @RequestParam String telefono, 
                                       @RequestParam String nuevaPassword, 
                                       Model model) {
        try {
            usuarioService.recuperarPassword(username, telefono, nuevaPassword);
            return "redirect:/login?mensaje=Contrasena actualizada con exito. Inicia sesion.";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "usuarios/recuperar";
        }
    }
}
