package es.uma.tesaw.proyecto_bancosol.entities;


public class Notificacion {

  private long idNotificacion;
  private long idPersonaDestino;
  private String idTipo;
  private String titulo;
  private String mensaje;
  private String leida;
  private java.sql.Timestamp fechaCreacion;
  private java.sql.Timestamp fechaEnvioProgramado;
  private long idAsignacionRef;


  public long getIdNotificacion() {
    return idNotificacion;
  }

  public void setIdNotificacion(long idNotificacion) {
    this.idNotificacion = idNotificacion;
  }


  public long getIdPersonaDestino() {
    return idPersonaDestino;
  }

  public void setIdPersonaDestino(long idPersonaDestino) {
    this.idPersonaDestino = idPersonaDestino;
  }


  public String getIdTipo() {
    return idTipo;
  }

  public void setIdTipo(String idTipo) {
    this.idTipo = idTipo;
  }


  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }


  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }


  public String getLeida() {
    return leida;
  }

  public void setLeida(String leida) {
    this.leida = leida;
  }


  public java.sql.Timestamp getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(java.sql.Timestamp fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }


  public java.sql.Timestamp getFechaEnvioProgramado() {
    return fechaEnvioProgramado;
  }

  public void setFechaEnvioProgramado(java.sql.Timestamp fechaEnvioProgramado) {
    this.fechaEnvioProgramado = fechaEnvioProgramado;
  }


  public long getIdAsignacionRef() {
    return idAsignacionRef;
  }

  public void setIdAsignacionRef(long idAsignacionRef) {
    this.idAsignacionRef = idAsignacionRef;
  }

}
