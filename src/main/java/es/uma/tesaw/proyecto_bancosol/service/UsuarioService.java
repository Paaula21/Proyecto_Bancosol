package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.UsuarioRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario auth(String username, String password) { return this.usuarioRepository.autenticar(username, password); }
}
