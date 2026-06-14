/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO los CP
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.CodigoPostalDTO;
import es.uma.tesaw.proyecto_bancosol.entities.CodigoPostal;
import org.springframework.stereotype.Component;

@Component
public class CodigoPostalMapper extends MapperDTO<CodigoPostalDTO, CodigoPostal> {

    @Override
    public CodigoPostalDTO toDTO(CodigoPostal entity) {
        if (entity == null) return null;

        CodigoPostalDTO dto = new CodigoPostalDTO();
        dto.setIdCp(entity.getIdCp());
        dto.setCodigo(entity.getCodigo());
        dto.setIdDivision(entity.getDivision() != null ? entity.getDivision().getIdDivision() : null);

        return dto;
    }
}
