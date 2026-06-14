/*
* Andrea Pérez Rodríguez: 66% (hola maría, como hemos hecho 2 y 1 había pensado en 66/33 pero como tú lo veas mejor jeje)
*
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
