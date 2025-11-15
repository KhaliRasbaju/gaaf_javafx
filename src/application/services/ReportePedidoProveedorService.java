package application.services;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import application.config.ApiConfig;
import application.models.response.ReportePedidoProveedor;
import application.utils.HttpClientUtil;

public class ReportePedidoProveedorService {

    // ================================
    //        MAPEO DEL JSON
    // ================================
	private final ObjectMapper mapper = new ObjectMapper();

    // ================================
    //        UBICACIÓN SERVICIO
    // ================================
	private static final String URL = ApiConfig.BASE_URL + "/reporte/pedido-proveedor";
	
    // ================================
    //        CONSTRUCTOR
    // ================================
    public ReportePedidoProveedorService() {
    	mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}


    // ================================
	//   OBTENER REPORTE DE PEDIDOS POR PROVEEDORES
	// ================================

	public ReportePedidoProveedor obtenerReportePedidoProveedor(
			LocalDate fechaPedido,
            LocalDate fechaEntrega,
            String estado,
            String metodoPago,
            Double valorPedido,
            Integer page
			) throws Exception {
		StringJoiner params = new StringJoiner("&");
		if (fechaPedido != null) params.add("fechaPedido=" + fechaPedido);
        if (fechaEntrega != null) params.add("fechaEntrega=" + fechaEntrega);
        if (estado != null && !estado.isEmpty()) params.add("estado=" + estado);
        if (metodoPago != null && !metodoPago.isEmpty()) params.add("metodoPago=" + metodoPago);
        if (valorPedido != null) params.add("valorPedido=" + valorPedido);
        if (page != null) params.add("page=" + page);
        String url = URL;
        if (params.length() > 0) url += "?" + params.toString();
        String response = HttpClientUtil.get(url, false);
        return mapper.readValue(response, ReportePedidoProveedor.class);
    }
}
