package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.NotificacionDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.NotificacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/notificaciones")
public class NotificacionesController {

    private final NotificacionService notificacionService;

    @GetMapping("")
    public String verNotificaciones(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam(required = false) Integer idNotificacion,
            Model model) {

        if (user == null) {
            return "redirect:/";
        }

        // 1. Obtener lista (el repo ya las trae filtradas y ordenadas)
        List<NotificacionDTO> notificaciones = notificacionService.obtenerNotificacionesUsuario(user.getIdPersona());
        model.addAttribute("notificaciones", notificaciones);

        // 2. Gestionar la leída
        NotificacionDTO seleccionada = notificacionService.obtenerYMarcarLeida(idNotificacion, user.getIdPersona());
        model.addAttribute("seleccionada", seleccionada);

        // 3. Contar no leídas para la vista
        long noLeidas = notificacionService.contarNoLeidas(user.getIdPersona());
        model.addAttribute("hayNoLeidas", noLeidas > 0);

        // Para mostrar en el header notificaciones sin leer
        model.addAttribute("hayNoLeidas", notificacionService.contarNoLeidas(user.getIdPersona()) > 0);

        return "notificaciones";
    }

    @PostMapping("/borrar")
    public String borrarNotificacion(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam("id") Integer idNotificacion) {

        if (user != null) {
            notificacionService.borrarNotificacion(idNotificacion, user.getIdPersona());
        }
        return "redirect:/notificaciones";
    }
}