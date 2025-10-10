package application.models.request;


public class CuentaRequest {

	private String numero;
	
	private String tipo;
	
	private Long idEntidad;

	public CuentaRequest() {}

	public CuentaRequest(String numero, String tipo, Long idEntidad) {
		this.numero = numero;
		this.tipo = tipo;
		this.idEntidad = idEntidad;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}

}
