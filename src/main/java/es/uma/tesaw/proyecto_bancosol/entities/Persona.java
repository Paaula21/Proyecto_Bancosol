package es.uma.tesaw.proyecto_bancosol.entities;


public class Persona {

  private long idPersona;
  private String nombreCompleto;
  private String telefono;
  private String email;
  private String observacion;


  public long getIdPersona() {
    return idPersona;
  }

  public void setIdPersona(long idPersona) {
    this.idPersona = idPersona;
  }


  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }


  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

}
