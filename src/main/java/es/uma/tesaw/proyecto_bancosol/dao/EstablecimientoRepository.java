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

    @Query("SELECT e FROM Campana camp JOIN camp.cadenas c JOIN c.establecimientos e " +
            "WHERE camp.idCampana = :idCampana " +
            "AND (:nombreCadena IS NULL OR :nombreCadena = '' OR LOWER(c.nombreCadena) LIKE LOWER(CONCAT('%', :nombreCadena, '%'))) " +
            "AND (:idEstablecimiento IS NULL OR e.idEstablecimiento = :idEstablecimiento)")
    List<Establecimiento> buscarEstablecimientosFiltrados(
            @Param("idCampana") String idCampana,
            @Param("nombreCadena") String nombreCadena,
            @Param("idEstablecimiento") Integer idEstablecimiento
    );

    @Query("SELECT COALESCE(MAX(e.idEstablecimiento), 0) FROM Establecimiento e")
    Integer findMaxId();

    @Query("SELECT DISTINCT e FROM Establecimiento e " +
            "LEFT JOIN e.direccion d " +
            "LEFT JOIN d.cp cp " +
            "LEFT JOIN cp.division div " +
            "LEFT JOIN div.zona z " +
            "LEFT JOIN e.cadena c " +
            "LEFT JOIN c.campanas camp " +
            "WHERE " +
            "(:idCadena IS NULL OR :idCadena = '' OR c.idCadena = :idCadena) AND " +
            "(:nombre IS NULL OR :nombre = '' OR LOWER(e.nombreResena) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:idCampana IS NULL OR :idCampana = '' OR camp.idCampana = :idCampana) AND " +
            "(:tipoVia IS NULL OR :tipoVia = '' OR d.tipoVia = :tipoVia) AND " +
            "(:nombreVia IS NULL OR :nombreVia = '' OR LOWER(d.nombreVia) LIKE LOWER(CONCAT('%', :nombreVia, '%'))) AND " +
            "(:codigo IS NULL OR :codigo = '' OR cp.codigo = :codigo) AND " +
            "(:localidad IS NULL OR :localidad = '' OR LOWER(div.nombreDivision) LIKE LOWER(CONCAT('%', :localidad, '%'))) AND " +
            "(:idZona IS NULL OR z.idZona = :idZona)")
    List<Establecimiento> findAllFiltrados(
            @Param("idCadena") String idCadena,
            @Param("nombre") String nombre,
            @Param("idCampana") String idCampana,
            @Param("tipoVia") String tipoVia,
            @Param("nombreVia") String nombreVia,
            @Param("codigo") String codigo,
            @Param("localidad") String localidad,
            @Param("idZona") Integer idZona
    );
}
