package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.VistaVoluntarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VistaVoluntariosRepository extends JpaRepository<VistaVoluntarios, Integer> {

    // Busca coincidencias ignorando mayúsculas/minúsculas en el nombre completo del voluntario
    List<VistaVoluntarios> findByNombreCompletoContainingIgnoreCase(String nombre);

    // Busca por la preferencia de horario o disponibilidad exacta
    List<VistaVoluntarios> findByPreferenciaHorarioContainingIgnoreCase(String disponibilidad);

    // Combina ambos criterios para búsquedas avanzadas directamente sobre la base de datos
    List<VistaVoluntarios> findByNombreCompletoContainingIgnoreCaseAndPreferenciaHorarioContainingIgnoreCase(String nombre, String disponibilidad);
}
