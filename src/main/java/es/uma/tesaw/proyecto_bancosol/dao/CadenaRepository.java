/**
 * Repositorio que utiliza JPQL para acceder a la base de datos en cadena.
 *
 * Autores:
 * - María Muñoz Martín: 100%
 */

package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CadenaRepository extends JpaRepository<Cadena, String> {

    @Query("SELECT c FROM Cadena c WHERE c.nombreCadena LIKE CONCAT('%', :nombre, '%')")
    List<Cadena> filtrarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Cadena c JOIN c.campanas camp WHERE camp.idCampana = :idCampana")
    List<Cadena> filtrarPorCampana(@Param("idCampana") String idCampana);

    @Query("SELECT c FROM Cadena c JOIN c.campanas camp WHERE camp.idCampana = :idCampana AND c.nombreCadena LIKE CONCAT('%', :nombre, '%')")    List<Cadena> filtrarPorNombreYCampana(@Param("nombre") String nombre,
                                          @Param("idCampana") String idCampana);
}