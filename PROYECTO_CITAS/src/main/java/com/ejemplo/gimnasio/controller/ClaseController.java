package com.ejemplo.gimnasio.controller;

import com.ejemplo.gimnasio.model.Clase;
import com.ejemplo.gimnasio.model.Usuario;
import com.ejemplo.gimnasio.service.ClaseService;
import com.ejemplo.gimnasio.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clases")
public class ClaseController {

    private final ClaseService claseService;
    private final UsuarioService usuarioService;

    public ClaseController(ClaseService claseService, UsuarioService usuarioService) {
        this.claseService = claseService;
        this.usuarioService = usuarioService;
    }

    // 1. Listar todas las clases
    @GetMapping
    public String listarClases(Model model) {
        List<Clase> clases = claseService.listarTodas();
        model.addAttribute("clases", clases);
        return "gimnasio/clases/lista";
    }

    // 2. Formulario para crear una nueva clase
    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model) {
        Clase clase = new Clase();
        // Valor por defecto para el cupo estricto
        clase.setCupoMaximo(5);
        
        model.addAttribute("clase", clase);
        model.addAttribute("modo", "crear");
        
        // Filtrar y enviar solo los usuarios que sean ENTRENADORES activos
        List<Usuario> entrenadores = usuarioService.listar(null).stream()
                .filter(u -> "ENTRENADOR".equals(u.getRol()) && u.isActivo())
                .collect(Collectors.toList());
        model.addAttribute("entrenadores", entrenadores);
        
        // Lista fija de tipos de clases permitidos
        model.addAttribute("tiposClases", List.of("Crossfit", "Yoga", "Zumba"));
        
        return "gimnasio/clases/formulario";
    }

    // 3. Guardar la nueva clase
    @PostMapping("/guardar")
    public String guardarClase(@Valid @ModelAttribute("clase") Clase clase, 
                               BindingResult result, 
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("modo", "crear");
            
            // Recargar entrenadores y tipos en caso de error
            List<Usuario> entrenadores = usuarioService.listar(null).stream()
                    .filter(u -> "ENTRENADOR".equals(u.getRol()) && u.isActivo())
                    .collect(Collectors.toList());
            model.addAttribute("entrenadores", entrenadores);
            model.addAttribute("tiposClases", List.of("Crossfit", "Yoga", "Zumba"));
            
            return "gimnasio/clases/formulario";
        }

        try {
            claseService.guardar(clase);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("modo", "crear");
            
            List<Usuario> entrenadores = usuarioService.listar(null).stream()
                    .filter(u -> "ENTRENADOR".equals(u.getRol()) && u.isActivo())
                    .collect(Collectors.toList());
            model.addAttribute("entrenadores", entrenadores);
            model.addAttribute("tiposClases", List.of("Crossfit", "Yoga", "Zumba"));
            
            return "gimnasio/clases/formulario";
        }

        return "redirect:/clases?mensaje=Clase creada con exito";
    }

    // 4. Eliminar una clase
    @PostMapping("/{id}/eliminar")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminar(id);
        return "redirect:/clases?mensaje=Clase eliminada correctamente";
    }
}