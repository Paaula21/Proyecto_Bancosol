package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.DivisionTerritorialRepository;
import es.uma.tesaw.proyecto_bancosol.entities.DivisionTerritorial;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DivisionTerritorialRepository extends JpaRepository<DivisionTerritorial, Integer> {
}
