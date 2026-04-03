package es.uma.tesaw.proyecto_bancosol.entities;


public class Direccion {

  private long idDireccion;
  private String tipoVia;
  private String nombreVia;
  private String numero;
  private String codigoPostal;
  private long idLocalidad;
  private long idDistrito;


  public long getIdDireccion() {
    return idDireccion;
  }

  public void setIdDireccion(long idDireccion) {
    this.idDireccion = idDireccion;
  }


  public String getTipoVia() {
    return tipoVia;
  }

  public void setTipoVia(String tipoVia) {
    this.tipoVia = tipoVia;
  }


  public String getNombreVia() {
    return nombreVia;
  }

  public void setNombreVia(String nombreVia) {
    this.nombreVia = nombreVia;
  }


  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }


  public String getCodigoPostal() {
    return codigoPostal;
  }

  public void setCodigoPostal(String codigoPostal) {
    this.codigoPostal = codigoPostal;
  }


  public long getIdLocalidad() {
    return idLocalidad;
  }

  public void setIdLocalidad(long idLocalidad) {
    this.idLocalidad = idLocalidad;
  }


  public long getIdDistrito() {
    return idDistrito;
  }

  public void setIdDistrito(long idDistrito) {
    this.idDistrito = idDistrito;
  }

}
