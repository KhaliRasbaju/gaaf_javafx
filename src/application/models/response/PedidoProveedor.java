package application.models.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PedidoProveedor {

	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaPedido;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEntrega;
	
    private String estado;
    
    private String proveedor;
    
    private String contactoProveedor;
    
    private String referenciaPago;
    
    private String metodoPago;
    
    private String nuemroCuenta;
    
    private String tipoCuenta;
    
    private String entidadBancaria;
    
    private Double valorPedido;
     
	// ================================
	//        CONSTRUCTORES
	// ================================
	public PedidoProveedor() {}

	public PedidoProveedor(Long id, LocalDate fechaPedido, LocalDate fechaEntrega, String estado,
			String proveedor, String contactoProveedor, String referenciaPago, String metodoPago, String nuemroCuenta,
			String tipoCuenta, String entidadBancaria, Double valorPedido) {
		this.id = id;
		this.fechaPedido = fechaPedido;
		this.fechaEntrega = fechaEntrega;
		this.estado = estado;
		this.proveedor = proveedor;
		this.contactoProveedor = contactoProveedor;
		this.referenciaPago = referenciaPago;
		this.metodoPago = metodoPago;
		this.nuemroCuenta = nuemroCuenta;
		this.tipoCuenta = tipoCuenta;
		this.entidadBancaria = entidadBancaria;
		this.valorPedido = valorPedido;
	}

	// ================================
	//        GETTERS & SETTERS
	// ================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDate fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getContactoProveedor() {
		return contactoProveedor;
	}

	public void setContactoProveedor(String contactoProveedor) {
		this.contactoProveedor = contactoProveedor;
	}

	public String getReferenciaPago() {
		return referenciaPago;
	}

	public void setReferenciaPago(String referenciaPago) {
		this.referenciaPago = referenciaPago;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getNuemroCuenta() {
		return nuemroCuenta;
	}

	public void setNuemroCuenta(String nuemroCuenta) {
		this.nuemroCuenta = nuemroCuenta;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getEntidadBancaria() {
		return entidadBancaria;
	}

	public void setEntidadBancaria(String entidadBancaria) {
		this.entidadBancaria = entidadBancaria;
	}

	public Double getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(Double valorPedido) {
		this.valorPedido = valorPedido;
	}

}
