package es.uma.tesaw.proyecto_bancosol.entities;


public class Colaborador {

  private String idColaborador;
  private String nombreColaborador;
  private String observaciones;
  private long idDireccion;


  public String getIdColaborador() {
    return idColaborador;
  }

  public void setIdColaborador(String idColaborador) {
    this.idColaborador = idColaborador;
  }


  public String getNombreColaborador() {
    return nombreColaborador;
  }

  public void setNombreColaborador(String nombreColaborador) {
    this.nombreColaborador = nombreColaborador;
  }


  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }


  public long getIdDireccion() {
    return idDireccion;
  }

  public void setIdDireccion(long idDireccion) {
    this.idDireccion = idDireccion;
  }

}
