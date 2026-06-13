package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper extends MapperDTO<UsuarioDTO, Usuario> {

    @Override
    public UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setIdPersona(entity.getPersona() != null ? entity.getPersona().getIdPersona() : null);
        dto.setIdRol(entity.getRol() != null ? entity.getRol().getIdRol() : null);
        dto.setNombreRol(entity.getRol().getNombreRol());
        dto.setUsuario(entity.getUsuario());

        return dto;
    }
}
