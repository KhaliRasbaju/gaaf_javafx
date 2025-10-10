<<<<<<< HEAD
package application.models.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReporteCompra {
	
    private Long idPedido;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPedido;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEntrega;
    
    private String estado;
    
    private String proveedor;
    
    private String contactoProveedor;
    
    private String telefonoProveedor;
    
    private String producto;
    
    private Integer cantidad;
    
    private Float peso;
    
    private Double valorPedido;

    public ReporteCompra() {}

	public ReporteCompra(Long idPedido, LocalDateTime fechaPedido, LocalDateTime fechaEntrega, String estado,
			String proveedor, String contactoProveedor, String telefonoProveedor, String producto, Integer cantidad,
			Float peso, Double valorPedido) {
		this.idPedido = idPedido;
		this.fechaPedido = fechaPedido;
		this.fechaEntrega = fechaEntrega;
		this.estado = estado;
		this.proveedor = proveedor;
		this.contactoProveedor = contactoProveedor;
		this.telefonoProveedor = telefonoProveedor;
		this.producto = producto;
		this.cantidad = cantidad;
		this.peso = peso;
		this.valorPedido = valorPedido;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDateTime getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
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

	public String getTelefonoProveedor() {
		return telefonoProveedor;
	}

	public void setTelefonoProveedor(String telefonoProveedor) {
		this.telefonoProveedor = telefonoProveedor;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Double getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(Double valorPedido) {
		this.valorPedido = valorPedido;
	}

}
=======
package application.models.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReporteCompra {
	
    private Long idPedido;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPedido;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEntrega;
    
    private String estado;
    
    private String proveedor;
    
    private String contactoProveedor;
    
    private String telefonoProveedor;
    
    private String producto;
    
    private Integer cantidad;
    
    private Float peso;
    
    private Double valorPedido;

    public ReporteCompra() {}

	public ReporteCompra(Long idPedido, LocalDateTime fechaPedido, LocalDateTime fechaEntrega, String estado,
			String proveedor, String contactoProveedor, String telefonoProveedor, String producto, Integer cantidad,
			Float peso, Double valorPedido) {
		this.idPedido = idPedido;
		this.fechaPedido = fechaPedido;
		this.fechaEntrega = fechaEntrega;
		this.estado = estado;
		this.proveedor = proveedor;
		this.contactoProveedor = contactoProveedor;
		this.telefonoProveedor = telefonoProveedor;
		this.producto = producto;
		this.cantidad = cantidad;
		this.peso = peso;
		this.valorPedido = valorPedido;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDateTime getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
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

	public String getTelefonoProveedor() {
		return telefonoProveedor;
	}

	public void setTelefonoProveedor(String telefonoProveedor) {
		this.telefonoProveedor = telefonoProveedor;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Double getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(Double valorPedido) {
		this.valorPedido = valorPedido;
	}

}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
