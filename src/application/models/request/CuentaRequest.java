package application.models.request;


public class CuentaRequest {

	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	
	private Long numero;
	
	private String tipo;
	
	private Long idEntidad;

	// ================================
	//          CONSTRUCTORES
	// ================================
	
	public CuentaRequest() {}

	public CuentaRequest(Long numero, String tipo, Long idEntidad) {
		this.numero = numero;
		this.tipo = tipo;
		this.idEntidad = idEntidad;
	}

	// ================================
	//        GETTERS Y SETTERS
	// ================================
	
	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getNumero() {
		return numero;
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
