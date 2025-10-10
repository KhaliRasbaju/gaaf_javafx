package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.ReporteInventarioProductoBodega;
import application.utils.HttpClientUtil;

public class ReporteInventarioProductoBodegaService {
	
	
	private final ObjectMapper mapper = new ObjectMapper();

    public List<ReporteInventarioProductoBodega> obtenerReporteReporteInventarioProductoBodega() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/reporte/producto-bodega");
        return mapper.readValue(response, new TypeReference<List<ReporteInventarioProductoBodega>>(){});
    }
}
