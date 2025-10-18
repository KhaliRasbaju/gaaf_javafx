package application.models.request;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequest {

	
    private Long nitProveedor;
    
    private Double valor;
    
    private LocalDateTime fechaPedido;
    
    private MedioPagoRequest medioPago;
    
    private List<DetallePedidoRequest> detalle;

    public PedidoRequest() {}

	public PedidoRequest(Long nitProveedor, Double valor, LocalDateTime fechaPedido, MedioPagoRequest medioPago,
			List<DetallePedidoRequest> detalle) {
		this.nitProveedor = nitProveedor;
		this.valor = valor;
		this.fechaPedido = fechaPedido;
		this.medioPago = medioPago;
		this.detalle = detalle;
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

	public MedioPagoRequest getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioPagoRequest medioPago) {
		this.medioPago = medioPago;
	}

	public List<DetallePedidoRequest> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetallePedidoRequest> detalle) {
		this.detalle = detalle;
	}
	
	private LocalDateTime fechaEntrega;
	private boolean recibido;

	public LocalDateTime getFechaEntrega() {
	    return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
	    this.fechaEntrega = fechaEntrega;
	}

	public boolean isRecibido() {
	    return recibido;
	}

	public void setRecibido(boolean recibido) {
	    this.recibido = recibido;
	}

	@Override
	public String toString() {
	    return "PedidoRequest{" +
	            "nitProveedor=" + nitProveedor +
	            ", valor=" + valor +
	            ", fechaPedido=" + fechaPedido +
	            ", medioPago=" + medioPago +
	            ", detalle=" + detalle +
	            ", fechaEntrega=" + fechaEntrega +
	            ", recibido=" + recibido +
	            '}';
	}

}
