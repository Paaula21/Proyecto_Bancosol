package es.uma.tesaw.proyecto_bancosol.entities;


public class Localidad {

  private long idLocalidad;
  private String nombreLocalidad;
  private long idZona;


  public long getIdLocalidad() {
    return idLocalidad;
  }

  public void setIdLocalidad(long idLocalidad) {
    this.idLocalidad = idLocalidad;
  }


  public String getNombreLocalidad() {
    return nombreLocalidad;
  }

  public void setNombreLocalidad(String nombreLocalidad) {
    this.nombreLocalidad = nombreLocalidad;
  }


  public long getIdZona() {
    return idZona;
  }

  public void setIdZona(long idZona) {
    this.idZona = idZona;
  }

}
