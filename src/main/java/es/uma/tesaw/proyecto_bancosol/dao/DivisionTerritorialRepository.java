package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.DivisionTerritorialRepository;
import es.uma.tesaw.proyecto_bancosol.entities.DivisionTerritorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DivisionTerritorialRepository extends JpaRepository<DivisionTerritorial, Integer> {
    Optional<DivisionTerritorial> findByNombreDivision(String nombreDivision);

    @Query("SELECT d FROM DivisionTerritorial d WHERE d.nombreDivision = :nombreDivision")
    List<DivisionTerritorial> buscarPorNombre(@Param("nombreDivision") String nombreDivision);
}
