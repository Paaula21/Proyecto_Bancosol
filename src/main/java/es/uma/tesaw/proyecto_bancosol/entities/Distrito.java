package es.uma.tesaw.proyecto_bancosol.entities;


public class Distrito {

  private long idDistrito;
  private String nombreDistrito;
  private long idLocalidad;


  public long getIdDistrito() {
    return idDistrito;
  }

  public void setIdDistrito(long idDistrito) {
    this.idDistrito = idDistrito;
  }


  public String getNombreDistrito() {
    return nombreDistrito;
  }

  public void setNombreDistrito(String nombreDistrito) {
    this.nombreDistrito = nombreDistrito;
  }


  public long getIdLocalidad() {
    return idLocalidad;
  }

  public void setIdLocalidad(long idLocalidad) {
    this.idLocalidad = idLocalidad;
  }

}
