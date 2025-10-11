package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import application.config.ApiConfig;
import application.models.response.ReportePedidoProveedor;
import application.utils.HttpClientUtil;

public class ReportePedidoProveedorService {

	private final ObjectMapper mapper = new ObjectMapper();

	
	
    public ReportePedidoProveedorService() {
    	mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}



	public List<ReportePedidoProveedor> obtenerReportePedidoProveedor() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/reporte/pedido-proveedor", false);
        return mapper.readValue(response, new TypeReference<List<ReportePedidoProveedor>>(){});
    }
}
