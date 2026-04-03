package es.uma.tesaw.proyecto_bancosol.entities;


public class AsignacionTurnoColaborador {

  private long idAsignacionTurno;
  private String idCampana;
  private long idTienda;
  private String idColaborador;
  private java.sql.Date fecha;
  private java.sql.Time horaInicio;
  private java.sql.Time horaFin;


  public long getIdAsignacionTurno() {
    return idAsignacionTurno;
  }

  public void setIdAsignacionTurno(long idAsignacionTurno) {
    this.idAsignacionTurno = idAsignacionTurno;
  }


  public String getIdCampana() {
    return idCampana;
  }

  public void setIdCampana(String idCampana) {
    this.idCampana = idCampana;
  }


  public long getIdTienda() {
    return idTienda;
  }

  public void setIdTienda(long idTienda) {
    this.idTienda = idTienda;
  }


  public String getIdColaborador() {
    return idColaborador;
  }

  public void setIdColaborador(String idColaborador) {
    this.idColaborador = idColaborador;
  }


  public java.sql.Date getFecha() {
    return fecha;
  }

  public void setFecha(java.sql.Date fecha) {
    this.fecha = fecha;
  }


  public java.sql.Time getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(java.sql.Time horaInicio) {
    this.horaInicio = horaInicio;
  }


  public java.sql.Time getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(java.sql.Time horaFin) {
    this.horaFin = horaFin;
  }

}
