package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CadenaRepository extends JpaRepository<Cadena, Integer> {

    // Busca las Cadenas que están dentro de la lista de cadenas de una Campaña específica.
    // Esta consulta es "a prueba de balas" porque partimos de la Campaña hacia sus cadenas.
    @Query("SELECT c FROM Campana camp JOIN camp.cadenas c WHERE camp.idCampana = :idCampana")
    List<Cadena> findCadenasByCampanaId(@Param("idCampana") String idCampana);

}