package application.models.request;

public class CredencialesRequest {

	// ================================
	//        ATRIBUTO PRINCIPAL
	// ================================
	private String contraseña;

	// ================================
	//          CONSTRUCTORES
	// ================================
	public CredencialesRequest() {
	}

	public CredencialesRequest(String contraseña) {
		this.contraseña = contraseña;
	}

	// ================================
	//        GETTERS Y SETTERS
	// ================================
	
	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	
}
