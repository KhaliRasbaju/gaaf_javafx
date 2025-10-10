package application.models.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Pedido {
	
	private Long id;
	
	private Long nitProveedor;
	
    private Double valor;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPedido;
    
    private Boolean recibido;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String fechaEntrega;
    
    private MedioPago medioPago;
    
    private List<DetallePedido> detallePedido;
    
	public Pedido() {}

	public Pedido(Long id, Long nitProveedor, Double valor, LocalDateTime fechaPedido, Boolean recibido,
			String fechaEntrega, MedioPago medioPago, List<DetallePedido> detallePedido) {
		this.id = id;
		this.nitProveedor = nitProveedor;
		this.valor = valor;
		this.fechaPedido = fechaPedido;
		this.recibido = recibido;
		this.fechaEntrega = fechaEntrega;
		this.medioPago = medioPago;
		this.detallePedido = detallePedido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(Long nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDateTime getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Boolean getRecibido() {
		return recibido;
	}

	public void setRecibido(Boolean recibido) {
		this.recibido = recibido;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public List<DetallePedido> getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(List<DetallePedido> detallePedido) {
		this.detallePedido = detallePedido;
	}

}
