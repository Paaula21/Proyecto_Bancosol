package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.NotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer>{
    @Query("SELECT n FROM Notificacion n WHERE n.personaDestino.idPersona = :idPersona ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findByPersonaDestinoOrderByFechaCreacionDesc(@Param("idPersona") Integer idPersona);

    @Query("SELECT COUNT(n) FROM Notificacion n WHERE n.personaDestino.idPersona = :idPersona AND n.leida = false")
    long countNoLeidasPorPersona(@Param("idPersona") Integer idPersona);
}
