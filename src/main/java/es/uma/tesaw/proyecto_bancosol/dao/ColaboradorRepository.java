package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ColaboradorRepository extends JpaRepository<Colaborador, String> {

    // Búsqueda solo por nombre
    @Query("SELECT c FROM Colaborador c WHERE LOWER(c.nombreColaborador) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Colaborador> findByNombreColaboradorContainingIgnoreCase(@Param("busqueda") String busqueda);

    // Búsqueda solo por zona
    @Query("SELECT c FROM Colaborador c WHERE c.direccion.cp.division.zona.nombreZona = :zona")
    List<Colaborador> findByZona(@Param("zona") String zona);

    // Búsqueda por nombre y zona a la vez
    @Query("SELECT c FROM Colaborador c WHERE LOWER(c.nombreColaborador) LIKE LOWER(CONCAT('%', :busqueda, '%')) AND c.direccion.cp.division.zona.nombreZona = :zona")
    List<Colaborador> findByNombreColaboradorContainingIgnoreCaseAndZona(@Param("busqueda") String busqueda, @Param("zona") String zona);
}