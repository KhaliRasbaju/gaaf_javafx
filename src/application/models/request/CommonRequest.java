package application.models.request;

public class CommonRequest {
	
    // ================================
    //        ATRIBUTO PRINCIPAL
    // ================================
	private String nombre;

    // ================================
    //          CONSTRUCTORES
    // ================================
	public CommonRequest() {}

	public CommonRequest(String nombre) {
		this.nombre = nombre;
	}

	// ================================
	//        GETTERS Y SETTERS
	// ================================
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
