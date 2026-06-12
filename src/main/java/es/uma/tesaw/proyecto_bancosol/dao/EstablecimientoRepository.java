package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // <-- Importación necesaria para el parámetro

import java.util.List;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Integer>{
    @Query("SELECT z.nombreZona, COUNT(e) FROM Establecimiento e JOIN e.direccion d JOIN d.cp cp JOIN cp.division div JOIN div.zona z " +
            "GROUP BY z.nombreZona ORDER BY COUNT(e) DESC")
    List<Object[]> countEstablecimientosPorZona();

    /*
     * Para la consulta necesito tanto la zona como el número total de tiendas para calcular luego el porcentaje,
     * por ello necesito un array de objetos donde en la primera posición tendremos un String y en la segunda un Long
     */

    // --- NUEVA CONSULTA AÑADIDA ---
    // Viaja desde el Establecimiento -> su Cadena -> las Campañas de esa cadena para filtrar por ID
    @Query("SELECT e FROM Establecimiento e JOIN e.cadena c, Campana camp WHERE c MEMBER OF camp.cadenas AND camp.idCampana = :idCampana")
    List<Establecimiento> findByCampanaId(@Param("idCampana") String idCampana);
}