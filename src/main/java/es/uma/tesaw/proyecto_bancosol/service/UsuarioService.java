package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.UsuarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.CambioContrasenaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

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
}
