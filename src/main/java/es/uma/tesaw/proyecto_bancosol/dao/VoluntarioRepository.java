/**
 * Repository que utiliza JPQL para acceder a la base de datos y obtener informacion de voluntario.
 *
 * Autores:
 * - Ainhoa García Rebollo: 100%
 */

package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.VoluntarioRepository;
import es.uma.tesaw.proyecto_bancosol.entities.VistaVoluntarios;
import es.uma.tesaw.proyecto_bancosol.entities.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Integer>{

    List<Voluntario> findByDisponibilidadContainingIgnoreCase(String disponibilidad);

    @Query("SELECT v FROM Voluntario v " +
            "LEFT JOIN FETCH v.persona " +
            "LEFT JOIN FETCH v.colaborador")
    List<Voluntario> ExportarVoluntarios();
}
