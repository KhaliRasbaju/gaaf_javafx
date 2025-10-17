package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.CommonRequest;
import application.models.response.Common;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class MetodoPagoService {

	
	private final ObjectMapper mapper = new ObjectMapper();
	 
	
	    public List<Common> obtenerMetodos() throws Exception {
	        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/metodo-pago", false);
	        return mapper.readValue(response, new TypeReference<List<Common>>() {});
	    }

	    public Common crearMetodo(CommonRequest entidad) throws Exception {
	        String jsonBody = mapper.writeValueAsString(entidad);
	        String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/metodo-pago/crear", jsonBody, false);
	        return mapper.readValue(response, Common.class);
	    }

	    public Common editarMetodo(Long id, CommonRequest entidad) throws Exception {
	        String jsonBody = mapper.writeValueAsString(entidad);
	        String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/metodo-pago/editar/" + id, jsonBody);
	        return mapper.readValue(response, Common.class);
	    }
	    
	    public Common obtenerMetodo(Long id) throws Exception {
	        String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/metodo-pago/" + id);
	        return mapper.readValue(response, Common.class);
	    }
	  
	    public ResponseCommon eliminarMetodo(Long id) throws Exception {
	        String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/metodo-pago/" + id);
	        return mapper.readValue(response, ResponseCommon.class);
	    }
	
}
