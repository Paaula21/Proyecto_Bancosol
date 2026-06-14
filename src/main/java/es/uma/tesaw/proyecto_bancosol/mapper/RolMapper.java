/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO los roles
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.RolDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper extends MapperDTO<RolDTO, Rol> {

    @Override
    public RolDTO toDTO(Rol entity) {
        if (entity == null) return null;

        RolDTO dto = new RolDTO();
        dto.setIdRol(entity.getIdRol());
        dto.setNombreRol(entity.getNombreRol());

        return dto;
    }
}
