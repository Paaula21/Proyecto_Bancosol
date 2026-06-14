/*
* Ainhoa García Rebollo: 50%
* Andrea Pérez Rodríguez: 50%
 */

package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Campana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampanaRepository extends JpaRepository<Campana, String> {  // String, no Integer
    @Query("SELECT c FROM Campana c WHERE c.estado = :estado")
    List<Campana> findByEstado(@Param("estado") String estado);

    @Query("SELECT c FROM Campana c WHERE (:estado = 'Todos' OR c.estado = :estado) AND LOWER(c.nombreCampana) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Campana> filtrarCampanas(@Param("estado") String estado, @Param("busqueda") String busqueda);
}