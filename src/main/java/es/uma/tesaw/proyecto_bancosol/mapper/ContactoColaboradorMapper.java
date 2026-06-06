package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.ContactoColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador;
import org.springframework.stereotype.Component;

@Component
public class ContactoColaboradorMapper extends MapperDTO<ContactoColaboradorDTO, ContactoColaborador> {

    @Override
    public ContactoColaboradorDTO toDTO(ContactoColaborador entity) {
        if (entity == null) return null;

        ContactoColaboradorDTO dto = new ContactoColaboradorDTO();
        dto.setIdContacto(entity.getIdContacto());
        dto.setIdPersona(entity.getPersona() != null ? entity.getPersona().getIdPersona() : null);
        dto.setIdColaborador(entity.getColaborador() != null ? entity.getColaborador().getIdColaborador() : null);
        dto.setEsPrincipal(entity.getEsPrincipal());

        return dto;
    }
}
