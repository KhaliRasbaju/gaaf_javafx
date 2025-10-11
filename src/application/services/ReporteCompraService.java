package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import application.config.ApiConfig;
import application.models.response.ReporteCompra;
import application.utils.HttpClientUtil;

public class ReporteCompraService {

    private final ObjectMapper mapper = new ObjectMapper();
    


    public ReporteCompraService() {
    	mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}



	public List<ReporteCompra> obtenerReporteCompras() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/reporte/compra", false);
        return mapper.readValue(response, new TypeReference<List<ReporteCompra>>(){});
    }


}
