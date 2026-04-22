package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.CodigoPostalRepository;
import es.uma.tesaw.proyecto_bancosol.entities.CodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, Integer>{
}
