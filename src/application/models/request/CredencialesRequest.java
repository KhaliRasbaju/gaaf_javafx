package application.models.request;

public class CredencialesRequest {

	private String contraseña;

	public CredencialesRequest() {
	}

	public CredencialesRequest(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	
}
