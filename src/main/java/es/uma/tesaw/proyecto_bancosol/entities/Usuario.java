package es.uma.tesaw.proyecto_bancosol.entities;


public class Usuario {

  private long idUsuario;
  private long idRol;
  private String contrasenia;


  public long getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(long idUsuario) {
    this.idUsuario = idUsuario;
  }


  public long getIdRol() {
    return idRol;
  }

  public void setIdRol(long idRol) {
    this.idRol = idRol;
  }


  public String getContrasenia() {
    return contrasenia;
  }

  public void setContrasenia(String contrasenia) {
    this.contrasenia = contrasenia;
  }

}
