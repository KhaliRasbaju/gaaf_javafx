package application.models.request;

public class SesionRequest {

	// ================================
		//        ATRIBUTOS PRINCIPALES
	// ================================
	
	private String usuario;
	
	private String contraseña;
	
	// ================================
		//          CONSTRUCTORES
	// ================================
	public SesionRequest() {}

	public SesionRequest(String usuario, String contraseña) {
		this.usuario = usuario;
		this.contraseña = contraseña;
	}
	// ================================
		//        GETTERS Y SETTERS
	// ================================
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
}
