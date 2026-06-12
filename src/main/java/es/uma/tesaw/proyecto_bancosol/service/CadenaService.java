package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.CadenaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import es.uma.tesaw.proyecto_bancosol.mapper.CadenaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CadenaService {

    private final CadenaRepository cadenaRepository;
    private final CampanaRepository campanaRepository;
    private final CadenaMapper cadenaMapper;

    public List<CadenaDTO> listarCadenas() {
        return this.listarCadenas(null, null);
    }

    public List<CadenaDTO> listarCadenas(String nombre, String idCampana) {
        List<Cadena> lista;
        boolean sinNombre  = (nombre == null || nombre.isEmpty());
        boolean sinCampana = (idCampana == null || idCampana.isEmpty());

        if (sinNombre && sinCampana) {
            lista = cadenaRepository.findAll();
        } else if (!sinNombre && sinCampana) {
            lista = cadenaRepository.filtrarPorNombre(nombre);
        } else if (sinNombre) {
            lista = cadenaRepository.filtrarPorCampana(idCampana);
        } else {
            lista = cadenaRepository.filtrarPorNombreYCampana(nombre, idCampana);
        }
        return cadenaMapper.toDTOList(lista);
    }

    public CadenaDTO buscarCadena(String idCadena) {
        if (idCadena == null || idCadena.isEmpty()) return new CadenaDTO();
        return cadenaMapper.toDTO(cadenaRepository.findById(idCadena).get());
    }

    public void guardarCadena(String idCadena, String nombreCadena, List<String> campanasIds) {
        Cadena cadena;
        if (idCadena == null || idCadena.isEmpty()) {
            cadena = new Cadena();
            cadena.setIdCadena(normalizarId(nombreCadena));
        } else {
            cadena = cadenaRepository.findById(idCadena).orElse(new Cadena());
            cadena.setIdCadena(idCadena);
        }
        cadena.setNombreCadena(nombreCadena);
        if (campanasIds != null) {
            cadena.setCampanas(campanaRepository.findAllById(campanasIds));
        } else {
            cadena.setCampanas(new ArrayList<>());
        }
        cadenaRepository.save(cadena);
    }

    private String normalizarId(String nombre) {
        return nombre.toUpperCase()
                     .replaceAll("\\s+", "_")
                     .replaceAll("[^A-Z0-9_]", "")
                     .replaceAll("_+", "_");
    }

    public void borrarCadena(String idCadena) {
        Cadena entidad = cadenaRepository.findById(idCadena).get();
        entidad.deleteCampanas();
        cadenaRepository.delete(entidad);
    }
}