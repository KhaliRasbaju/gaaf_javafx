package application.models.response;

public class MedioPago {
	
	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	
	private Long id;
	
	private String referencia;
	
	private String metodoPago;
	
	
	// ================================
	//        CONSTRUCTORES
	// ================================
	public MedioPago() {}

	public MedioPago(Long id, String referencia, String metodoPago) {
		this.id = id;
		this.referencia = referencia;
		this.metodoPago = metodoPago;
	}
	
	// ================================
	//        GETTERS & SETTERS
	// ================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	
}
