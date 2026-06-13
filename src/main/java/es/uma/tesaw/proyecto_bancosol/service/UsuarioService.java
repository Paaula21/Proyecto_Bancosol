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

        // 1. Validaciones básicas (Las mismas que tenías en React)
        if (!dto.getNueva().equals(dto.getConfirmacion())) {
            throw new IllegalArgumentException("Las contraseñas nuevas no coinciden.");
        }

        if (dto.getNueva().length() < 8) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 8 caracteres.");
        }

        // 2. Buscar al usuario
        Usuario usuario = this.usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 3. Comprobar que la contraseña actual es correcta
        // Nota: Si en el futuro usas BCrypt o encriptación, aquí usarías passwordEncoder.matches()
        if (!usuario.getContrasenia().equals(dto.getActual())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta.");
        }

        // 4. Guardar la nueva contraseña
        usuario.setContrasenia(dto.getNueva());
        this.usuarioRepository.save(usuario);
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
