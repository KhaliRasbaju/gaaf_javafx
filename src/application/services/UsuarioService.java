package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.CredencialesRequest;
import application.models.request.UsuarioRequest;
import application.models.response.ResponseCommon;
import application.models.response.Usuario;
import application.models.response.UsuarioResponse;
import application.utils.HttpClientUtil;

public class UsuarioService {

	
	 private final ObjectMapper mapper = new ObjectMapper();


	    public List<UsuarioResponse> obtenerUsuarios() throws Exception {
	        String response = HttpClientUtil.get(ApiConfig.BASE_AUTH_URL + "/usuario", false);
	        return mapper.readValue(response, new TypeReference<List<UsuarioResponse>>() {});
	    }
	  
	    public ResponseCommon editarUsuario(String id, UsuarioRequest request) throws Exception {
	        String jsonBody = mapper.writeValueAsString(request);
	        String response = HttpClientUtil.put(ApiConfig.BASE_AUTH_URL + "/usuario/editar/" + id, jsonBody);
	        return mapper.readValue(response, ResponseCommon.class);
	    }
	    
	    public ResponseCommon editarCredenciales(String id, CredencialesRequest request) throws Exception {
	        String jsonBody = mapper.writeValueAsString(request);
	        String response = HttpClientUtil.put(ApiConfig.BASE_AUTH_URL + "/usuario/credenciales/" + id, jsonBody);
	        return mapper.readValue(response, ResponseCommon.class);
	    }
	    
	    
	    public ResponseCommon editarEstado(String id) throws Exception {
	        String response = HttpClientUtil.put(ApiConfig.BASE_AUTH_URL + "/usuario/estado/" + id);
	        return mapper.readValue(response, ResponseCommon.class);
	    }
	    
	    public UsuarioResponse obtenerUsuario(String id) throws Exception {
	        String response = HttpClientUtil.get(ApiConfig.BASE_AUTH_URL + "/usuario/"+id, false);
	        return mapper.readValue(response, UsuarioResponse.class);
	    }

	   
	    public ResponseCommon eliminarUsuario(String id) throws Exception {
	        String response = HttpClientUtil.delete(ApiConfig.BASE_AUTH_URL + "/usuario/" + id);
	        return mapper.readValue(response, ResponseCommon.class);
	    }
	
}
