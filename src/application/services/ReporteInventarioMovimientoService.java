package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.ReporteInventarioMovimiento;
import application.utils.HttpClientUtil;

public class ReporteInventarioMovimientoService {
	
	private final ObjectMapper mapper = new ObjectMapper();

    public List<ReporteInventarioMovimiento> obtenerReporteInventarioMovimiento() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/reporte/inventario-movimiento");
        return mapper.readValue(response, new TypeReference<List<ReporteInventarioMovimiento>>(){});
    }
    
}
