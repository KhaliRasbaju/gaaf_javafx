package application.models.response;

public class Municipio {
	
	private  Long id;
	
	private String nombre;
	
	private String departameto;
	
	public Municipio() {}

	public Municipio(Long id, String nombre, String departameto) {
		this.id = id;
		this.nombre = nombre;
		this.departameto = departameto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDepartameto() {
		return departameto;
	}

	public void setDepartameto(String departameto) {
		this.departameto = departameto;
	}
	
}
