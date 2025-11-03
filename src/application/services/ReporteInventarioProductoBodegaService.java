package application.services;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import application.config.ApiConfig;
import application.models.response.ReporteInventarioProductoBodega;
import application.utils.HttpClientUtil;

public class ReporteInventarioProductoBodegaService {
	
	
	private final ObjectMapper mapper = new ObjectMapper();

	private static final String URL = ApiConfig.BASE_URL + "/reporte/producto-bodega"; 
	
	
	
	public ReporteInventarioProductoBodegaService() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
	
    public ReporteInventarioProductoBodega obtenerReporteReporteInventarioProductoBodega(
    		String producto,
    		Integer cantidad,
    		LocalDate fecha,
    		Integer page
    		) throws Exception {
    	StringJoiner params = new StringJoiner("&");
    	if (producto != null && !producto.isEmpty()) params.add("producto=" + producto);
    	if (cantidad != null) params.add("cantidad=" + cantidad);
    	if(fecha != null) params.add("fecha=" + fecha);
		if (page != null) params.add("page=" + page);	
		String url = URL;
	    if (params.length() > 0) url += "?" + params.toString();
	    String response = HttpClientUtil.get(url, false);
        
        return mapper.readValue(response, ReporteInventarioProductoBodega.class);
    }
}
