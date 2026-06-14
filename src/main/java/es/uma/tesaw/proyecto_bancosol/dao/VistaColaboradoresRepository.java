/**
 * Repositorio que utiliza JPQL para acceder a la base de datos de la vista de colaboradores.
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.VistaColaboradores;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VistaColaboradoresRepository extends JpaRepository<VistaColaboradores, String> {

    List<VistaColaboradores> findByNombreColaboradorContainingIgnoreCase(String busqueda);

    List<VistaColaboradores> findByNombreZona(String zona);

    List<VistaColaboradores> findByNombreColaboradorContainingIgnoreCaseAndNombreZona(String busqueda, String zona);
}