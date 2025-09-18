package application.models;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
	private Long nitProveedor;
    private List<String> productos;
    private Integer cantidad;
    private Double valor;
    private String fechaPedido;
    private Boolean recibido;
    private String fechaEntrega;

    
    public Pedido() {}
    
    // Constructor
    public Pedido(Long nitProveedor, List<String> productos, Integer cantidad, Double valor,
    		String fechaPedido, Boolean recibido, String fechaEntrega) {
        this.nitProveedor = nitProveedor;
        this.productos = productos;
        this.cantidad = cantidad;
        this.valor = valor;
        this.fechaPedido = fechaPedido;
        this.recibido = recibido;
        this.fechaEntrega = fechaEntrega;
    }

    // Getters y Setters
    public Long getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(Long nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public List<String> getProductos() {
        return productos;
    }

    public void setProductos(List<String> productos) {
        this.productos = productos;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
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
}
