package application.services;

import java.time.LocalDate;
import java.util.StringJoiner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import application.config.ApiConfig;
import application.models.response.ReporteCompra;
import application.utils.HttpClientUtil;

public class ReporteCompraService {

    private final ObjectMapper mapper = new ObjectMapper();
    
    private static final String URL = ApiConfig.BASE_URL + "/reporte/compra";


    public ReporteCompraService() {
    	mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}



	public ReporteCompra obtenerReporteCompras(
			LocalDate fechaPedido,
            LocalDate fechaEntrega,
            String estado,
            String producto,
            Integer cantidad,
            Double valorPedido,
            Integer page) throws Exception {
		StringJoiner params = new StringJoiner("&");
		if (fechaPedido != null) params.add("fechaPedido=" + fechaPedido);
        if (fechaEntrega != null) params.add("fechaEntrega=" + fechaEntrega);
        if (estado != null && !estado.isEmpty()) params.add("estado=" + estado);
        if (producto != null && !producto.isEmpty()) params.add("producto=" + producto);
        if (cantidad != null) params.add("cantidad=" + cantidad);
        if (valorPedido != null) params.add("valorPedido=" + valorPedido);
        if (page != null) params.add("page=" + page);
        String url = URL;
        if (params.length() > 0) url += "?" + params.toString();
        String response = HttpClientUtil.get(url, false);
        return mapper.readValue(response, ReporteCompra.class);
    }


}
