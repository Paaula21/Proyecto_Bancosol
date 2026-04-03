package es.uma.tesaw.proyecto_bancosol.entities;


public class Campana {

  private String idCampana;
  private String nombreCampana;
  private java.sql.Date fechaInicio;
  private java.sql.Date fechaFin;


  public String getIdCampana() {
    return idCampana;
  }

  public void setIdCampana(String idCampana) {
    this.idCampana = idCampana;
  }


  public String getNombreCampana() {
    return nombreCampana;
  }

  public void setNombreCampana(String nombreCampana) {
    this.nombreCampana = nombreCampana;
  }


  public java.sql.Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(java.sql.Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }


  public java.sql.Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(java.sql.Date fechaFin) {
    this.fechaFin = fechaFin;
  }

}
