package com.ejemplo.gimnasio.controller;

import com.ejemplo.gimnasio.model.Usuario;
import com.ejemplo.gimnasio.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal; 

@Controller 
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    // ejercicio 3
    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("usuarios", service.listar(q));
        model.addAttribute("q", q); 
        return "usuarios/lista";
    }

    // Pagina detalle, ejercicio 5
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            Usuario usuario = service.buscarPorId(id);
            model.addAttribute("usuario", usuario);
            return "usuarios/detalle";
        } catch (IllegalArgumentException e) {
            // Maneja el ID que no existe mostrando un mensaje amigable y redirigiendo a la lista
            flash.addFlashAttribute("error", "El usuario con ID " + id + " no existe.");
            return "redirect:/usuarios";
        }
    }

    // Crear Usuarios 
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("modo","crear");
        return "usuarios/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult errores, Model model, RedirectAttributes flash) {
                if (errores.hasErrors()){
                    model.addAttribute("modo", "crear");
                    return "usuarios/formulario";
                }
                try {
                    service.crear(usuario);
                    flash.addFlashAttribute("mensaje", "Usuario creado correctamente");
                    return "redirect:/usuarios";
                } catch (IllegalArgumentException e){
                    model.addAttribute("modo", "crear");
                    model.addAttribute("error", e.getMessage());
                    return "usuarios/formulario";
                }
    }

    /// Actualizar usuarios 
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Usuario u = service.buscarPorId(id);
        u.setPassword("");
        model.addAttribute("usuario", u);
        model.addAttribute("modo", "editar");
        return "usuarios/formulario";
    }

    @PostMapping("/{id}/actualizar")
    public String actualizar(@PathVariable Long id, 
                           @Valid @ModelAttribute ("usuario") Usuario usuario, 
                           BindingResult errores, Model model, RedirectAttributes flash){
        if(errores.hasErrors()){
            usuario.setId(id);
            model.addAttribute("modo", "editar");
            return "usuarios/formulario";
        }
        try {
            service.actualizar(id, usuario);
            flash.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
            return "redirect:/usuarios";
        } catch (IllegalArgumentException e){
            usuario.setId(id);
            model.addAttribute("modo", "editar");
            model.addAttribute("error", e.getMessage());
            return "usuarios/formulario";
        }
    }
    
    // Auto eliminacion
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, Principal principal, RedirectAttributes flash) {
        Usuario usuarioAEliminar = service.buscarPorId(id);

        if (usuarioAEliminar.getUsername().equals(principal.getName())) {
            flash.addFlashAttribute("error", "No puedes eliminar tu propia cuenta de administrador");
            return "redirect:/usuarios";
        }

        service.eliminar(id);
        flash.addFlashAttribute("mensaje", "Usuario eliminado con exito!");
        return "redirect:/usuarios";
    }
    
}