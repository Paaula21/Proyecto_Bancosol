package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dao.AsignacionTurnoColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.EstablecimientoRepository;
import es.uma.tesaw.proyecto_bancosol.entities.AsignacionTurnoColaborador;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import es.uma.tesaw.proyecto_bancosol.service.EstablecimientoService;
import es.uma.tesaw.proyecto_bancosol.service.HistorialService;
import es.uma.tesaw.proyecto_bancosol.service.VoluntariosService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CampanaController {

    private final CampanaService campanaService;
    private final HistorialService historialService;
    private final EstablecimientoService establecimientoService;
    private final VoluntariosService voluntariosService;

    private final CampanaRepository campanaRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final AsignacionTurnoColaboradorRepository asignacionTurnoRepository;

    private static final Map<String, String> DIAS_ES = Map.of(
            "MONDAY",    "lunes",
            "TUESDAY",   "martes",
            "WEDNESDAY", "miercoles",
            "THURSDAY",  "jueves",
            "FRIDAY",    "viernes",
            "SATURDAY",  "sabado"
    );

    private boolean sinPermiso(Usuario user) {
        return user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2);
    }

    @GetMapping("/campanas")
    public String verCampanas(@SessionAttribute(name = "user", required = false) Usuario user, Model model) {
        if (sinPermiso(user)) return "redirect:/dashboard";

        model.addAttribute("campanas", campanaService.listarCampanas());
        return "campanas";
    }

    @GetMapping("/historial")
    public String verHistorial(@SessionAttribute(name = "user", required = false) Usuario user, Model model) {
        if (sinPermiso(user)) return "redirect:/dashboard";

        model.addAttribute("logs", historialService.listarHistorial());
        return "historial";
    }

    @GetMapping("/campanas/turnos")
    public String verTurnosCampana(@RequestParam("id") String idCampana,
                                   @RequestParam(value = "cadena", required = false) String nombreCadena,
                                   @RequestParam(value = "idTienda", required = false) String idTiendaStr,
                                   @SessionAttribute(name = "user", required = false) Usuario user,
                                   Model model) {

        if (sinPermiso(user)) return "redirect:/dashboard";

        model.addAttribute("idCampana", idCampana);
        model.addAttribute("cadenaSeleccionada", nombreCadena);
        model.addAttribute("idTiendaBuscado", idTiendaStr);

        Integer idTienda = null;
        if (idTiendaStr != null && !idTiendaStr.isBlank()) {
            try {
                idTienda = Integer.parseInt(idTiendaStr.trim());
            } catch (NumberFormatException e) {
                idTienda = -1;
            }
        }

        model.addAttribute("establecimientos",
                establecimientoService.buscarEstablecimientosPorCampanaConFiltros(idCampana, nombreCadena, idTienda));

        return "listadoCampanas";
    }

    @GetMapping("/campanas/asignacion")
    public String irAAsignacionTurnos(@RequestParam("idCampana") String idCampana,
                                      @RequestParam("idTienda") String idTienda,
                                      @SessionAttribute(name = "user", required = false) Usuario user,
                                      Model model) {

        if (sinPermiso(user)) return "redirect:/dashboard";

        model.addAttribute("idCampana", idCampana);
        model.addAttribute("idTienda", idTienda);
        model.addAttribute("voluntariosManana", voluntariosService.listarVoluntarios("mañana"));
        model.addAttribute("voluntariosTarde",  voluntariosService.listarVoluntarios("tarde"));

        Map<String, String> asignacionesGuardadas = new HashMap<>();

        campanaRepository.findById(idCampana).ifPresent(campana -> {
            establecimientoRepository.findById(Integer.parseInt(idTienda)).ifPresent(tienda -> {

                List<AsignacionTurnoColaborador> turnos = asignacionTurnoRepository.findByCampanaAndTienda(campana, tienda);

                for (AsignacionTurnoColaborador t : turnos) {
                    String diaEs = DIAS_ES.get(t.getFecha().getDayOfWeek().name());
                    if (diaEs == null) continue;

                    String turno = (t.getHoraInicio().getHour() < 14) ? "manana" : "tarde";

                    if (t.getVoluntario() != null) {
                        asignacionesGuardadas.put("asignacion_" + turno + "_" + diaEs, String.valueOf(t.getVoluntario().getIdVoluntario()));
                    }
                }
            });
        });

        model.addAttribute("asignaciones", asignacionesGuardadas);
        return "AsignacionTurnos";
    }

    @PostMapping("/campanas/turnos/guardar")
    public String guardarTurnos(HttpServletRequest request,
                                @RequestParam("idCampana") String idCampana,
                                @RequestParam("idTienda") String idTienda,
                                @SessionAttribute(name = "user", required = false) Usuario user) {

        if (sinPermiso(user)) return "redirect:/dashboard";

        campanaService.guardarTurnos(idCampana, idTienda, request);

        return "redirect:/campanas/asignacion?idCampana=" + idCampana + "&idTienda=" + idTienda;
    }
}