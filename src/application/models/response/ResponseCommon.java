package application.models.response;

public class ResponseCommon {

	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	private Integer status;
	
	private String message; 
	
	// ================================
	//        CONSTRUCTORES
	// ================================
	public ResponseCommon() {}
	
	public ResponseCommon(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	// ================================
	//        GETTERS & SETTERS
	// ================================
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
