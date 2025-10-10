<<<<<<< HEAD
package application.models.response;

public class Ubicacion {
	
	private Long id;
	
	private String direccion;
	
	private Municipio municipio;
	
	public Ubicacion() {}

	public Ubicacion(Long id, String direccion, Municipio municipio) {
		this.id = id;
		this.direccion = direccion;
		this.municipio = municipio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
}
=======
package application.models.response;

public class Ubicacion {
	
	private Long id;
	
	private String direccion;
	
	private Municipio municipio;
	
	public Ubicacion() {}

	public Ubicacion(Long id, String direccion, Municipio municipio) {
		this.id = id;
		this.direccion = direccion;
		this.municipio = municipio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
