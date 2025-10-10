package application.models.response;

public class ResponseCommon {

	private Integer status;
	
	private String message; 
	
	public ResponseCommon() {}
	
	public ResponseCommon(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

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
