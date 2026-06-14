package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.CadenaService;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import es.uma.tesaw.proyecto_bancosol.service.IncidenciaService;
import es.uma.tesaw.proyecto_bancosol.service.NotificacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@AllArgsConstructor
public class IncidenciaController {

    private final IncidenciaService incidenciaService;
    private final CampanaService campanaService;
    private final CadenaService cadenaService;
    private final NotificacionService notificacionService;

    @GetMapping("/incidencias")
    public String mostrarFormulario(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            Model model) {
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("campanas", campanaService.listarCampanasDTO());
        model.addAttribute("todasCadenas", cadenaService.listarCadenas());

        // Para mostrar en el header notificaciones sin leer
        model.addAttribute("hayNoLeidas", notificacionService.contarNoLeidas(user.getIdPersona()) > 0);

        return "incidencias";
    }

    @PostMapping("/incidencias/guardar")
    public String guardarIncidencia(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam String rol,
            @RequestParam String nombre_persona,
            @RequestParam String id_campana,
            @RequestParam(required = false) String id_cadena,
            @RequestParam(required = false) String tienda,
            @RequestParam(defaultValue = "Lunes") String turno_dia,
            @RequestParam(defaultValue = "Mañana") String turno_franja,
            @RequestParam String urgencia,
            @RequestParam String descripcion) {
        if (user == null) {
            return "redirect:/";
        }

        incidenciaService.guardar(rol, nombre_persona, id_campana, id_cadena,
                tienda, turno_dia, turno_franja, urgencia, descripcion,
                user.getIdPersona());
        return "redirect:/dashboard";
    }
}
