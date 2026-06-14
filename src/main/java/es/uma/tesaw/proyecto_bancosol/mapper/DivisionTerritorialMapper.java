/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO las divisiones territoriales
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.DivisionTerritorialDTO;
import es.uma.tesaw.proyecto_bancosol.entities.DivisionTerritorial;
import org.springframework.stereotype.Component;

@Component
public class DivisionTerritorialMapper extends MapperDTO<DivisionTerritorialDTO, DivisionTerritorial> {

    @Override
    public DivisionTerritorialDTO toDTO(DivisionTerritorial entity) {
        if (entity == null) return null;

        DivisionTerritorialDTO dto = new DivisionTerritorialDTO();
        dto.setIdDivision(entity.getIdDivision());
        dto.setNombreDivision(entity.getNombreDivision());
        dto.setTipo(entity.getTipo());
        dto.setIdZona(entity.getZona() != null ? entity.getZona().getIdZona() : null);

        return dto;
    }
}
