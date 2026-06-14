/**
 * Repositorio que utiliza JPQL para acceder a la base de datos a los usuarios.
 * Autoras:
 * - Andrea Pérez Rodríguez: 75%
 * - María Muñoz Martín: 25%
 */

package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    @Query("SELECT u FROM Usuario u WHERE u.usuario = :nombreUsuario and u.contrasenia = :contraseniaUsuario")
    public Usuario autenticar (@Param("nombreUsuario")String usuario, @Param("contraseniaUsuario")String contrasenia);

    @Query("SELECT u FROM Usuario u WHERE u.rol.idRol = :rol")
    public List<Usuario> countByIdRol(@Param("rol") Integer rol);

    @Query("SELECT u FROM Usuario u WHERE u.rol.idRol = :idRol")
    List<Usuario> findByRolId(@Param("idRol") Integer idRol);
}
