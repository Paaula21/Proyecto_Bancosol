package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.NotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer>{
}
