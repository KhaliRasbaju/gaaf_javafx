package application.models.response;

public class Common {

	private Long id;
	
	private Long nombre;
	
	public Common() {
	}

	public Common(Long id, Long nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getNombre() {
		return nombre;
	}
	
	public void setNombre(Long nombre) {
		this.nombre = nombre;
	}
	
}
