package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.LogCampana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogCampanaRepository extends JpaRepository<LogCampana, Integer> {

    /**
     * Recupera todos los registros de la base de datos y los ordena
     * por la columna 'timestamp' de manera descendente (los más recientes primero).
     */
    List<LogCampana> findAllByOrderByTimestampDesc();

}