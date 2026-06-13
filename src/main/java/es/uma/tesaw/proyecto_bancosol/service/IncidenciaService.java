package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.NotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.dao.PersonaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.TipoNotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Notificacion;
import es.uma.tesaw.proyecto_bancosol.entities.Persona;
import es.uma.tesaw.proyecto_bancosol.entities.TipoNotificacion;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IncidenciaService {

    private static final String TIPO_INCIDENCIA_ID = "INCIDENCIA";

    private final CampanaService campanaService;
    private final CadenaService cadenaService;
    private final NotificacionRepository notificacionRepository;
    private final TipoNotificacionRepository tipoNotificacionRepository;
    private final PersonaRepository personaRepository;

    @PostConstruct
    public void garantizarTipoIncidencia() {
        if (tipoNotificacionRepository.findById(TIPO_INCIDENCIA_ID).isEmpty()) {
            TipoNotificacion tipo = new TipoNotificacion();
            tipo.setIdTipo(TIPO_INCIDENCIA_ID);
            tipo.setDescripcion("Incidencia");
            tipoNotificacionRepository.save(tipo);
        }
    }

    public void guardar(String rol, String nombrePersona, String idCampana,
                        String idCadena, String tienda, String turnoDia,
                        String turnoFranja, String urgencia, String descripcion,
                        Integer idPersona) {

        String nombreCampana = campanaService.listarCampanasDTO().stream()
                .filter(c -> c.getIdCampana().equals(idCampana))
                .findFirst()
                .map(c -> c.getNombreCampana())
                .orElse("");

        String nombreCadena = "";
        if (idCadena != null && !idCadena.isEmpty()) {
            nombreCadena = cadenaService.listarCadenas().stream()
                    .filter(c -> c.getIdCadena().equals(idCadena))
                    .findFirst()
                    .map(c -> c.getNombreCadena())
                    .orElse("");
        }

        String tiendaTexto = (tienda != null && !tienda.trim().isEmpty()) ? tienda.trim() : "No especificada";

        String mensaje = String.format(
                "Cadena: %s\nTienda: %s\nTurno: %s %s\nUrgencia: %s\nDescripción: %s\nPersona: %s\nRol: %s",
                nombreCadena.isEmpty() ? "-" : nombreCadena,
                tiendaTexto,
                turnoDia, turnoFranja,
                urgencia,
                descripcion,
                nombrePersona,
                rol
        );

        String titulo = nombreCampana.isEmpty() ? "Incidencia" : "Incidencia en " + nombreCampana;

        TipoNotificacion tipo = tipoNotificacionRepository.findById(TIPO_INCIDENCIA_ID)
                .orElseThrow(() -> new RuntimeException("Tipo de notificación INCIDENCIA no encontrado"));

        Persona persona = personaRepository.findById(idPersona)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada para el usuario"));

        Notificacion notificacion = new Notificacion();
        notificacion.setPersonaDestino(persona);
        notificacion.setTipo(tipo);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setLeida(false);

        notificacionRepository.save(notificacion);
    }
}
