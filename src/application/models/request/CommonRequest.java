package application.models.request;

public class CommonRequest {
	
	private String nombre;

	public CommonRequest() {}

	public CommonRequest(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
