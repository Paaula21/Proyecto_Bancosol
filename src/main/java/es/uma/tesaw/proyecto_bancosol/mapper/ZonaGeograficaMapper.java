/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO las xonas geográficas
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.ZonaGeograficaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica;
import org.springframework.stereotype.Component;

@Component
public class ZonaGeograficaMapper extends MapperDTO<ZonaGeograficaDTO, ZonaGeografica> {

    @Override
    public ZonaGeograficaDTO toDTO(ZonaGeografica entity) {
        if (entity == null) return null;

        ZonaGeograficaDTO dto = new ZonaGeograficaDTO();
        dto.setIdZona(entity.getIdZona());
        dto.setNombreZona(entity.getNombreZona());

        return dto;
    }
}
