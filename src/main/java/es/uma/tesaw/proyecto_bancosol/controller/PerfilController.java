/*
Paula Fernández Jiménez: 100%
*/

package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.CambioContrasenaDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class PerfilController {

    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model) {
        UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("user");

        // Redirección de seguridad si no hay sesión activa
        if (usuarioLogueado == null) {
            return "redirect:/";
        }

        // Los mensajes de éxito o error que vengan de RedirectAttributes se pasan solos a la vista
        return "perfil";
    }

    @PostMapping("/perfil/cambiar_contrasena")
    public String procesarCambioContrasena(
            @ModelAttribute CambioContrasenaDTO cambioContrasenaDTO,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("user");
        if (usuarioLogueado == null) {
            return "redirect:/";
        }

        try {
            this.usuarioService.cambiarContrasena(usuarioLogueado.getIdUsuario(), cambioContrasenaDTO);
            redirectAttributes.addFlashAttribute("mensajeTexto", "¡Contraseña actualizada con éxito!");
            redirectAttributes.addFlashAttribute("mensajeTipo", "exito");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTexto", e.getMessage());
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
        }

        return "redirect:/perfil";
    }
}