package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Integer>{

    @Query("SELECT z.nombreZona, COUNT(e) FROM Establecimiento e JOIN e.direccion d JOIN d.cp cp JOIN cp.division div JOIN div.zona z " +
            "GROUP BY z.nombreZona ORDER BY COUNT(e) DESC")
    List<Object[]> countEstablecimientosPorZona();

    @Query("SELECT e FROM Establecimiento e JOIN e.cadena c, Campana camp WHERE c MEMBER OF camp.cadenas AND camp.idCampana = :idCampana")
    List<Establecimiento> findByCampanaId(@Param("idCampana") String idCampana);

    @Query("SELECT e FROM Establecimiento e JOIN e.cadena c, Campana camp " +
            "WHERE c MEMBER OF camp.cadenas AND camp.idCampana = :idCampana " +
            "AND (:nombreCadena IS NULL OR :nombreCadena = '' OR LOWER(c.nombreCadena) LIKE LOWER(CONCAT('%', :nombreCadena, '%'))) " +
            "AND (:idEstablecimiento IS NULL OR e.idEstablecimiento = :idEstablecimiento)")
    List<Establecimiento> buscarEstablecimientosFiltrados(
            @Param("idCampana") String idCampana,
            @Param("nombreCadena") String nombreCadena,
            @Param("idEstablecimiento") Integer idEstablecimiento
    );
}