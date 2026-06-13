package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dao.NotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.dao.PersonaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.TipoNotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Notificacion;
import es.uma.tesaw.proyecto_bancosol.entities.Persona;
import es.uma.tesaw.proyecto_bancosol.entities.TipoNotificacion;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.CadenaService;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import es.uma.tesaw.proyecto_bancosol.service.IncidenciaService;
import jakarta.annotation.PostConstruct;
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
    private final NotificacionRepository notificacionRepository;
    private final TipoNotificacionRepository tipoNotificacionRepository;
    private final PersonaRepository personaRepository;

    @PostConstruct
    public void garantizarTipoIncidencia() {
        if (tipoNotificacionRepository.findById("INCIDENCIA").isEmpty()) {
            TipoNotificacion tipo = new TipoNotificacion();
            tipo.setIdTipo("INCIDENCIA");
            tipo.setDescripcion("Incidencia");
            tipoNotificacionRepository.save(tipo);
        }
    }

    @GetMapping("/incidencias")
    public String mostrarFormulario(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            Model model) {
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("campanas", campanaService.listarCampanasDTO());
        model.addAttribute("todasCadenas", cadenaService.listarCadenas());
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

        String nombreCampana = campanaService.listarCampanasDTO().stream()
                .filter(c -> c.getIdCampana().equals(id_campana))
                .findFirst()
                .map(c -> c.getNombreCampana())
                .orElse("");

        String nombreCadena = "";
        if (id_cadena != null && !id_cadena.isEmpty()) {
            String finalIdCadena = id_cadena;
            nombreCadena = cadenaService.listarCadenas().stream()
                    .filter(c -> c.getIdCadena().equals(finalIdCadena))
                    .findFirst()
                    .map(c -> c.getNombreCadena())
                    .orElse("");
        }

        String tiendaTexto = (tienda != null && !tienda.trim().isEmpty()) ? tienda.trim() : "No especificada";

        String mensaje = String.format(
                "Cadena: %s\nTienda: %s\nTurno: %s %s\nUrgencia: %s\nDescripción: %s\nPersona: %s\nRol: %s",
                nombreCadena.isEmpty() ? "-" : nombreCadena,
                tiendaTexto,
                turno_dia, turno_franja,
                urgencia,
                descripcion,
                nombre_persona,
                rol
        );

        String titulo = nombreCampana.isEmpty() ? "Incidencia" : "Incidencia en " + nombreCampana;

        TipoNotificacion tipo = tipoNotificacionRepository.findById("INCIDENCIA")
                .orElseThrow(() -> new RuntimeException("Tipo de notificación INCIDENCIA no encontrado"));

        Persona persona = personaRepository.findById(user.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada para el usuario"));
        Notificacion notificacion = new Notificacion();
        notificacion.setPersonaDestino(persona);
        notificacion.setTipo(tipo);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setLeida(false);

        notificacionRepository.save(notificacion);

        return "redirect:/dashboard";
    }
}
