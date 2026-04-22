package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.VoluntarioRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VoluntarioRepository extends JpaRepository<Voluntario, Integer>{
}
