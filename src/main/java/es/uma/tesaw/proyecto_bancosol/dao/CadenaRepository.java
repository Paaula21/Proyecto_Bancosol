package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CadenaRepository extends JpaRepository<Cadena, String> {

    @Query("select c from Cadena c where c.nombreCadena like concat('%', :nombre, '%')")
    List<Cadena> filtrarPorNombre(@Param("nombre") String nombre);

    @Query("select c from Cadena c join c.campanas camp where camp.idCampana = :idCampana")
    List<Cadena> filtrarPorCampana(@Param("idCampana") String idCampana);

    @Query("select c from Cadena c join c.campanas camp where " +
           "camp.idCampana = :idCampana and " +
           "c.nombreCadena like concat('%', :nombre, '%')")
    List<Cadena> filtrarPorNombreYCampana(@Param("nombre") String nombre,
                                          @Param("idCampana") String idCampana);

}