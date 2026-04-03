package es.uma.tesaw.proyecto_bancosol.entities;


public class ContactoColaborador {

  private long idContacto;
  private String idColaborador;
  private String esPrincipal;


  public long getIdContacto() {
    return idContacto;
  }

  public void setIdContacto(long idContacto) {
    this.idContacto = idContacto;
  }


  public String getIdColaborador() {
    return idColaborador;
  }

  public void setIdColaborador(String idColaborador) {
    this.idColaborador = idColaborador;
  }


  public String getEsPrincipal() {
    return esPrincipal;
  }

  public void setEsPrincipal(String esPrincipal) {
    this.esPrincipal = esPrincipal;
  }

}
