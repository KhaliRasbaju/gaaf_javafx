package application.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.ReporteInventarioResponse;
import application.utils.HttpClientUtil;

public class ReporteInventarioService {

	
	public ReporteInventarioResponse getReporte() throws Exception{
		String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/inventario/reporte");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response, ReporteInventarioResponse.class);
	}
}
