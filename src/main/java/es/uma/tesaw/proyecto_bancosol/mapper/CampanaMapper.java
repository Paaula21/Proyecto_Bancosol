/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO las campañas
 *Autores:
 *- Paula Fernández Jiménez: 70%
 *- María Muñoz Martín: 30%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import es.uma.tesaw.proyecto_bancosol.entities.Campana;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CampanaMapper extends MapperDTO<CampanaDTO, Campana> {

    @Override
    public CampanaDTO toDTO(Campana entity) {
        if (entity == null) return null;

        CampanaDTO dto = new CampanaDTO();
        dto.setIdCampana(entity.getIdCampana());
        dto.setNombreCampana(entity.getNombreCampana());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setEstado(entity.getEstado());
        dto.setIdsCadenas(entity.getCadenas().stream()
                .map(Cadena::getIdCadena)
                .collect(Collectors.toSet()));

        return dto;
    }
}
