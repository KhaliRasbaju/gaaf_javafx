package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.ProveedorRequest;
import application.models.response.Proveedor;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class ProveedorService {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	public List<Proveedor> obtenerTodos() throws Exception{
		String response = HttpClientUtil.get(ApiConfig.BASE_URL+"/proveedor", false);
		return mapper.readValue(response, new TypeReference<List<Proveedor>>() {});
	}
	
	public Proveedor crearProveedor(ProveedorRequest proveedor) throws Exception {
		String jsonBody = mapper.writeValueAsString(proveedor);
	    String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/proveedor/crear", jsonBody, false);
	    return mapper.readValue(response, Proveedor.class);
	}
	
	public Proveedor editarProveedor(Long nit, ProveedorRequest proveedor) throws Exception {
	    String jsonBody = mapper.writeValueAsString(proveedor);
	    String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/proveedor/editar/" + nit, jsonBody);
	    return mapper.readValue(response, Proveedor.class);
	}
	
	public Proveedor obtenerProveedor(Long nit) throws Exception {
	    String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/proveedor/" + nit, false);
	    return mapper.readValue(response, Proveedor.class); 
	}
	

	public ResponseCommon eliminarProveedor(Long nit) throws Exception {
	    String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/proveedor/" + nit);
	    return mapper.readValue(response, ResponseCommon.class); 
	}

}
