package es.uma.tesaw.proyecto_bancosol.entities;


public class AsignacionCoordinador {

  private long idAsignacionCoord;
  private String idCampana;
  private long idTienda;
  private long idUsuarioCoordinador;


  public long getIdAsignacionCoord() {
    return idAsignacionCoord;
  }

  public void setIdAsignacionCoord(long idAsignacionCoord) {
    this.idAsignacionCoord = idAsignacionCoord;
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


  public long getIdUsuarioCoordinador() {
    return idUsuarioCoordinador;
  }

  public void setIdUsuarioCoordinador(long idUsuarioCoordinador) {
    this.idUsuarioCoordinador = idUsuarioCoordinador;
  }

}
