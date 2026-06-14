/**
 * Service que maneja todas las funciones relacionadas con el manejo de las notificaciones
 * Autores:
 * - Andrea Pérez Rodríguez: 85%
 * - IA Generativa: 15%
 */

package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.NotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.dao.TipoNotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.dao.UsuarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.NotificacionDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Notificacion;
import es.uma.tesaw.proyecto_bancosol.entities.TipoNotificacion;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.mapper.NotificacionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final TipoNotificacionRepository tipoNotificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacionMapper notificacionMapper;


    public List<NotificacionDTO> obtenerNotificacionesUsuario(Integer idPersona) {
        return notificacionMapper.toDTOList(
                notificacionRepository.findByPersonaDestinoOrderByFechaCreacionDesc(idPersona)
        );
    }


    public long contarNoLeidas(Integer idPersona) {
        return notificacionRepository.countNoLeidasPorPersona(idPersona);
    }


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


    public void borrarNotificacion(Integer idNotificacion, Integer idPersonaLogueada) {
        Notificacion notif = notificacionRepository.findById(idNotificacion).orElse(null);
        if (notif != null && notif.getPersonaDestino().getIdPersona().equals(idPersonaLogueada)) {
            notificacionRepository.delete(notif);
        }
    }


    public void notificarACoordinadores(String titulo, String mensaje) {
        List<Usuario> coordinadores = usuarioRepository.findByRolId(2);
        TipoNotificacion tipoCampana = tipoNotificacionRepository.findById("CAMPANA").orElse(null);

        if (tipoCampana != null) {
            for (Usuario coordinador : coordinadores) {
                Notificacion n = new Notificacion();
                n.setPersonaDestino(coordinador.getPersona());
                n.setTipo(tipoCampana);
                n.setTitulo(titulo);
                n.setMensaje(mensaje);
                n.setLeida(false);
                notificacionRepository.save(n);
            }
        }
    }
}