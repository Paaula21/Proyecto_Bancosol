package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.TipoNotificacionRepository;
import es.uma.tesaw.proyecto_bancosol.entities.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion, String>{
}
