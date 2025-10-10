package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.ReportePedidoProveedor;
import application.utils.HttpClientUtil;

public class ReportePedidoProveedorService {

	private final ObjectMapper mapper = new ObjectMapper();

    public List<ReportePedidoProveedor> obtenerReporteCompras() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/reporte/pedido-proveedor");
        return mapper.readValue(response, new TypeReference<List<ReportePedidoProveedor>>(){});
    }
}
