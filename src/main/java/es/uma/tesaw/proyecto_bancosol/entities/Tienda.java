package es.uma.tesaw.proyecto_bancosol.entities;


public class Tienda {

  private long idTienda;
  private String idCadena;
  private String nombreResena;
  private long lineales;
  private long idDireccion;


  public long getIdTienda() {
    return idTienda;
  }

  public void setIdTienda(long idTienda) {
    this.idTienda = idTienda;
  }


  public String getIdCadena() {
    return idCadena;
  }

  public void setIdCadena(String idCadena) {
    this.idCadena = idCadena;
  }


  public String getNombreResena() {
    return nombreResena;
  }

  public void setNombreResena(String nombreResena) {
    this.nombreResena = nombreResena;
  }


  public long getLineales() {
    return lineales;
  }

  public void setLineales(long lineales) {
    this.lineales = lineales;
  }


  public long getIdDireccion() {
    return idDireccion;
  }

  public void setIdDireccion(long idDireccion) {
    this.idDireccion = idDireccion;
  }

}
