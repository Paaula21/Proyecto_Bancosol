package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.NotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.dao.PersonaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.TipoNotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.dao.UsuarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.NotificacionDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Notificacion;
import es.uma.tesaw.proyecto_bancosol.entities.TipoNotificacion;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.mapper.NotificacionMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final TipoNotificacionRepository tipoNotificacionRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacionMapper notificacionMapper;


    @Transactional(readOnly = true)
    public List<NotificacionDTO> obtenerNotificacionesUsuario(Integer idPersona) {
        return notificacionMapper.toDTOList(
                notificacionRepository.findByPersonaDestinoOrderByFechaCreacionDesc(idPersona)
        );
    }

    @Transactional(readOnly = true)
    public long contarNoLeidas(Integer idPersona) {
        return notificacionRepository.countNoLeidasPorPersona(idPersona);
    }

    @Transactional
    public NotificacionDTO obtenerYMarcarLeida(Integer idNotificacion, Integer idPersonaLogueada) {
        if (idNotificacion == null) return null;

        Notificacion notif = notificacionRepository.findById(idNotificacion).orElse(null);
        if (notif != null && notif.getPersonaDestino().getIdPersona().equals(idPersonaLogueada)) {
            if (!notif.getLeida()) {
                notif.setLeida(true);
                notificacionRepository.save(notif);
            }
            return notificacionMapper.toDTO(notif);
        }
        return null;
    }

    @Transactional
    public void borrarNotificacion(Integer idNotificacion, Integer idPersonaLogueada) {
        Notificacion notif = notificacionRepository.findById(idNotificacion).orElse(null);
        if (notif != null && notif.getPersonaDestino().getIdPersona().equals(idPersonaLogueada)) {
            notificacionRepository.delete(notif);
        }
    }

    @Transactional
    public void notificarACoordinadores(String titulo, String mensaje) {
        // Obtenemos a todos los usuarios que son Coordinadores (Rol = 2)
        List<Usuario> coordinadores = usuarioRepository.findByRolId(2);
        TipoNotificacion tipoSistema = tipoNotificacionRepository.findById("CAMPANA").orElse(null);

        if (tipoSistema != null) {
            for (Usuario coordinador : coordinadores) {
                Notificacion n = new Notificacion();
                n.setPersonaDestino(coordinador.getPersona());
                n.setTipo(tipoSistema);
                n.setTitulo(titulo);
                n.setMensaje(mensaje);
                n.setLeida(false);
                notificacionRepository.save(n);
            }
        }
    }
}