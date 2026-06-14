/*
Paula Fernández Jiménez: 100%
*/

package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.CambioContrasenaDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.service.NotificacionService;
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
    private final NotificacionService notificacionService;

    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model) {
        UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("user");

        // Redirección de seguridad si no hay sesión activa
        if (usuarioLogueado == null) {
            return "redirect:/";
        }

        // Para mostrar en el header notificaciones sin leer
        model.addAttribute("hayNoLeidas", notificacionService.contarNoLeidas(usuarioLogueado.getIdPersona()) > 0);

        return "perfil";
    }

    @PostMapping("/perfil/cambiar-contrasena")
    public String procesarCambioContrasena(
            @ModelAttribute CambioContrasenaDTO cambioContrasenaDTO,
            HttpSession session,
            Model model) {

        UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("user");
        if (usuarioLogueado == null) {
            return "redirect:/";
        }

        try {
            this.usuarioService.cambiarContrasena(usuarioLogueado.getIdUsuario(), cambioContrasenaDTO);
            model.addAttribute("mensajeTexto", "¡Contraseña actualizada con éxito!");
            model.addAttribute("mensajeTipo", "exito");
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeTexto", e.getMessage());
            model.addAttribute("mensajeTipo", "error");
        }

        return "perfil";
    }
}