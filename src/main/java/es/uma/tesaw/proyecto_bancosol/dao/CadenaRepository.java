package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.CadenaRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CadenaRepository extends JpaRepository<Cadena, String> {
}
