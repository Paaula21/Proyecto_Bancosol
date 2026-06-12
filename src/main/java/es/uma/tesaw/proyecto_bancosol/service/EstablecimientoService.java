package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.EstablecimientoRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Establecimiento;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EstablecimientoService {

    private final EstablecimientoRepository establecimientoRepository;

    public List<Establecimiento> buscarEstablecimientosPorCampana(String idCampana) {
        return establecimientoRepository.findByCampanaId(idCampana);
    }

    public List<Establecimiento> buscarEstablecimientosPorCampanaConFiltros(String idCampana, String nombreCadena, Integer idTienda) {
        String cadenaFiltro = (nombreCadena != null) ? nombreCadena.trim() : null;

        // Actualizado con el nuevo nombre de método del repositorio
        return establecimientoRepository.buscarEstablecimientosFiltrados(idCampana, cadenaFiltro, idTienda);
    }
}