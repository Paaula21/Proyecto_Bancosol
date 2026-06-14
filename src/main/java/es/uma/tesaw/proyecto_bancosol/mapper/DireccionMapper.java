/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO las direcciones
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.DireccionDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Direccion;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper extends MapperDTO<DireccionDTO, Direccion> {

    @Override
    public DireccionDTO toDTO(Direccion entity) {
        if (entity == null) return null;

        DireccionDTO dto = new DireccionDTO();
        dto.setIdDireccion(entity.getIdDireccion());
        dto.setTipoVia(entity.getTipoVia());
        dto.setNombreVia(entity.getNombreVia());
        dto.setNumero(entity.getNumero());
        dto.setIdCp(entity.getCp() != null ? entity.getCp().getIdCp() : null);

        return dto;
    }
}
