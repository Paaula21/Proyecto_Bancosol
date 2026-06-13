package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.UsuarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.CambioContrasenaDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional(readOnly = true)
    public List<Usuario> listarCoordinadores() {
        return usuarioRepository.findByRolId(2);
    }

    @Transactional
    public void cambiarContrasena(Integer idUsuario, CambioContrasenaDTO dto) {
        Usuario usuario = this.usuarioRepository.findById(idUsuario).get();

        if(!usuario.getContrasenia().equals(dto.getActual())){
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }
        if(!dto.getNueva().equals(dto.getConfirmacion())) {
            throw new IllegalArgumentException("La contraseña de confirmación no es igual que la escrita");
        }
        if(dto.getNueva().length() < 8){
            throw new IllegalArgumentException("La contraseña debe de contener mínimo 8 caracteres.");
        }

        usuario.setContrasenia(dto.getNueva());
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO autenticar(String username, String password) {
        Usuario usuario = this.usuarioRepository.autenticar(username, password);
        if (usuario == null) {
            return null;
        }
        return usuarioMapper.toDTO(usuario);
    }
}
