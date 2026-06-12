package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.service.CadenaService;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import es.uma.tesaw.proyecto_bancosol.service.EstablecimientoService;
import es.uma.tesaw.proyecto_bancosol.service.HistorialService; // Importamos el nuevo servicio
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@AllArgsConstructor
public class CampanaController {

    private final CampanaService campanaService;
    private final HistorialService historialService; // Inyectamos el servicio de historial
    private final CadenaService cadenaService;
    private final EstablecimientoService establecimientoService;

    @GetMapping("/campanas")
    public String verCampanas (@SessionAttribute(name = "user", required = false) Usuario user, Model model) {
        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            return "redirect:/dashboard";
        }
        model.addAttribute("campanas", campanaService.listarCampanas());
        return "campanas";
    }

    // ACTUALIZAMOS ESTE MÉTODO
    @GetMapping("/historial")
    public String verHistorial(@SessionAttribute(name = "user", required = false) Usuario user, Model model) {
        // Control de seguridad: solo los que pueden ver campañas ven el historial
        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            return "redirect:/dashboard";
        }

        // Buscamos los logs en la BBDD y los añadimos al modelo
        model.addAttribute("logs", historialService.listarHistorial());

        return "historial"; // Renderiza historial.jsp
    }


    // 1. RECUERDA INYECTAR EL SERVICIO ARRIBA EN TU CONTROLADOR:
    // private final EstablecimientoService establecimientoService;

    @GetMapping("/campanas/turnos")
    public String verTurnosCampana(@RequestParam("id") String idCampana,
                                   @SessionAttribute(name = "user", required = false) Usuario user,
                                   Model model) {

        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("idCampana", idCampana);

        // Enviamos la lista de establecimientos reales a la vista
        model.addAttribute("establecimientos", establecimientoService.buscarEstablecimientosPorCampana(idCampana));

        return "listadoCampanas";
    }
}