package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.VistaVoluntarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VistaVoluntariosRepository extends JpaRepository<VistaVoluntarios, String> {

    List<VistaVoluntarios> findByNombreCompletoContainingIgnoreCase(String nombre);

    List<VistaVoluntarios> findByDisponibilidadContainingIgnoreCase(String disponibilidad);

    List<VistaVoluntarios> findByNombreCompletoContainingIgnoreCaseAndDisponibilidadContainingIgnoreCase(
            String nombre,
            String disponibilidad);
}