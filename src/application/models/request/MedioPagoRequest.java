package application.models.request;

public class MedioPagoRequest {
	
    // ================================
    //        ATRIBUTOS PRINCIPALES
    // ================================
	 private String referencia;
	 
	 private Long idMetodoPago;
	 
	// ================================
	//          CONSTRUCTORES
	// ================================
	 public MedioPagoRequest() {}

	 public MedioPagoRequest(String referencia, Long idMetodoPago) {
		this.referencia = referencia;
		this.idMetodoPago = idMetodoPago;
	 }

	 // ================================
	 //        GETTERS Y SETTERS
	 // ================================
	 public String getReferencia() {
		 return referencia;
	 }

	 public void setReferencia(String referencia) {
		 this.referencia = referencia;
	 }

	 public Long getIdMetodoPago() {
		 return idMetodoPago;
	 }

	 public void setIdMetodoPago(Long idMetodoPago) {
		 this.idMetodoPago = idMetodoPago;
	 }
}
