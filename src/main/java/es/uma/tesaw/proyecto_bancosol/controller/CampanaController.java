package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dao.AsignacionTurnoColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.EstablecimientoRepository;
import es.uma.tesaw.proyecto_bancosol.dao.VoluntarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.AsignacionTurnoColaborador;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import es.uma.tesaw.proyecto_bancosol.service.EstablecimientoService;
import es.uma.tesaw.proyecto_bancosol.service.HistorialService; // Importamos el nuevo servicio
import es.uma.tesaw.proyecto_bancosol.service.VoluntariosService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

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
    // Añade estos tres junto a los que ya tienes:
    private final CampanaRepository campanaRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final AsignacionTurnoColaboradorRepository asignacionTurnoRepository;
    private final VoluntarioRepository voluntarioRepository;

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
                                   @RequestParam(value = "cadena", required = false) String nombreCadena,
                                   @RequestParam(value = "idTienda", required = false) String idTiendaStr,
                                   @SessionAttribute(name = "user", required = false) Usuario user,
                                   Model model) {

        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("idCampana", idCampana);

        // Enviamos los filtros de vuelta para mantener el texto escrito en la pantalla
        model.addAttribute("cadenaSeleccionada", nombreCadena);
        model.addAttribute("idTiendaBuscado", idTiendaStr);

        // Validamos el ID de la tienda de forma segura (por si llega vacío o con letras)
        Integer idTienda = null;
        if (idTiendaStr != null && !idTiendaStr.trim().isEmpty()) {
            try {
                idTienda = Integer.parseInt(idTiendaStr.trim());
            } catch (NumberFormatException e) {
                idTienda = -1; // Valor bandera en caso de error de formato para que no devuelva resultados
            }
        }

        // Buscamos los establecimientos aplicando los filtros correspondientes
        model.addAttribute("establecimientos", establecimientoService.buscarEstablecimientosPorCampanaConFiltros(idCampana, nombreCadena, idTienda));

        return "listadoCampanas";
    }

    @GetMapping("/campanas/asignacion")
    public String irAAsignacionTurnos(@RequestParam("idCampana") String idCampana,
                                      @RequestParam("idTienda") String idTienda,
                                      @SessionAttribute(name = "user", required = false) Usuario user,
                                      Model model) {

        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("idCampana", idCampana);
        model.addAttribute("idTienda", idTienda);

        // 1. Voluntarios para los desplegables
        model.addAttribute("voluntariosManana", voluntariosService.listarVoluntarios("Mañana"));
        model.addAttribute("voluntariosTarde",  voluntariosService.listarVoluntarios("Tarde"));

        // 2. Cargamos las asignaciones ya guardadas en BD para preseleccionar los <select>
        Map<String, String> asignacionesGuardadas = new HashMap<>();

        Campana campana = campanaRepository.findById(idCampana).orElse(null);
        Establecimiento tienda = establecimientoRepository.findById(Integer.parseInt(idTienda)).orElse(null);

        if (campana != null && tienda != null) {

            // Mapa para traducir el día en inglés (Java) al español sin tildes (nombre del <select>)
            Map<String, String> traduccionDias = new HashMap<>();
            traduccionDias.put("MONDAY",    "lunes");
            traduccionDias.put("TUESDAY",   "martes");
            traduccionDias.put("WEDNESDAY", "miercoles");
            traduccionDias.put("THURSDAY",  "jueves");
            traduccionDias.put("FRIDAY",    "viernes");
            traduccionDias.put("SATURDAY",  "sabado");

            List<AsignacionTurnoColaborador> turnos =
                    asignacionTurnoRepository.findByCampanaAndTienda(campana, tienda);

            for (AsignacionTurnoColaborador t : turnos) {

                // Traducimos el día de inglés a español (sin tildes, igual que los name del JSP)
                String diaEn = t.getFecha().getDayOfWeek().name(); // "MONDAY", "TUESDAY"...
                String diaEs = traduccionDias.get(diaEn);
                if (diaEs == null) continue; // domingo u otro día no contemplado, lo ignoramos

                // Mañana = hora inicio antes de las 14:00
                String turno = (t.getHoraInicio().getHour() < 14) ? "manana" : "tarde";

                // La clave debe coincidir exactamente con el name del <select> en el JSP
                String clave = "asignacion_" + turno + "_" + diaEs;

                // El valor es el ID del voluntario como String (para comparar en el JSP)
                if (t.getVoluntario() != null) {
                    asignacionesGuardadas.put(clave, String.valueOf(t.getVoluntario().getIdVoluntario()));
                }
            }
        }

        model.addAttribute("asignaciones", asignacionesGuardadas);

        return "AsignacionTurnos";
    }

    @PostMapping("/campanas/turnos/guardar")
    public String guardarTurnos(HttpServletRequest request,
                                @RequestParam("idCampana") String idCampana,
                                @RequestParam("idTienda") String idTienda,
                                @SessionAttribute(name = "user", required = false) Usuario user) {

        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            return "redirect:/dashboard";
        }

        campanaService.guardarTurnos(idCampana, idTienda, request);

        return "redirect:/campanas/asignacion?idCampana=" + idCampana + "&idTienda=" + idTienda;
    }
}