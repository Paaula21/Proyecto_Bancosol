package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dao.AsignacionTurnoColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.EstablecimientoRepository;
import es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.AsignacionTurnoColaborador;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.service.CadenaService;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import es.uma.tesaw.proyecto_bancosol.service.EstablecimientoService;
import es.uma.tesaw.proyecto_bancosol.service.HistorialService;
import es.uma.tesaw.proyecto_bancosol.service.VoluntariosService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CampanaController {

    private final CampanaService campanaService;
    private final CadenaService cadenaService; // Necesario para listar cadenas al editar/crear
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

    // ==========================================
    // GESTIÓN DE CAMPAÑAS (CRUD Y FILTROS)
    // ==========================================

    @GetMapping("/campanas")
    public String verCampanas(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam(required = false, defaultValue = "Todos") String estado,
            @RequestParam(required = false, defaultValue = "") String busqueda,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String accion,
            Model model) {

        if (sinPermiso(user)) return "redirect:/dashboard";

        // 1. Filtrado de campañas (Añadiremos este método en el Service)
        List<CampanaDTO> campanas = campanaService.listarCampanasDTO(estado, busqueda);

        // 2. Lógica del modoPanel (Igual que en Colaboradores)
        String modoPanel = "ninguno";
        CampanaDTO campanaSeleccionada = null;

        if (id != null && !id.isEmpty()) {
            campanaSeleccionada = campanaService.buscarCampana(id);
            if (campanaSeleccionada != null) {
                if ("editar".equals(accion)) {
                    modoPanel = "editar";
                } else {
                    modoPanel = "detalle";
                }
            }
        } else if ("nuevo".equals(accion)) {
            modoPanel = "anadir";
        }

        // 3. Pasamos los datos a la vista
        model.addAttribute("campanas", campanas);
        model.addAttribute("campanaSeleccionada", campanaSeleccionada);
        model.addAttribute("modoPanel", modoPanel);
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("busquedaFiltro", busqueda);

        // Si vamos a crear o editar, necesitamos la lista de cadenas para poder asignarlas
        if ("editar".equals(modoPanel) || "anadir".equals(modoPanel)) {
            model.addAttribute("todasCadenas", cadenaService.listarCadenas());
        }

        return "campanas"; // Llama a campanas.jsp
    }

    @PostMapping("/campanas/guardar")
    public String guardarCampana(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam(required = false) String idCampana,
            @RequestParam String nombreCampana,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam String estado,
            @RequestParam(required = false) List<String> cadenasIds) {

        if (sinPermiso(user)) return "redirect:/";

        // Añadiremos este método en el Service
        String idGuardado = campanaService.guardarCampana(idCampana, nombreCampana, fechaInicio, fechaFin, estado, cadenasIds);

        // Redirigimos a la vista de detalle de la campaña guardada
        return "redirect:/campanas?id=" + idGuardado;
    }

    @PostMapping("/campanas/eliminar")
    public String eliminarCampana(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam("id") String idCampana) {

        if (sinPermiso(user)) return "redirect:/";

        campanaService.borrarCampana(idCampana);
        return "redirect:/campanas";
    }

    // ==========================================
    // HISTORIAL Y ASIGNACIÓN DE TURNOS (Intacto)
    // ==========================================

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