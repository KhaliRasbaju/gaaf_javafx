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
	
    // ================================
    //        MAPEO DEL JSON
    // ================================
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	// ================================
	//     OBTENER LISTA PROVEEDORES
	// ================================
	public List<Proveedor> obtenerProveedores() throws Exception{
		String response = HttpClientUtil.get(ApiConfig.BASE_URL+"/proveedor", false);
		return mapper.readValue(response, new TypeReference<List<Proveedor>>() {});
	}
	
	// ================================
	//      CREAR NUEVO PROVEEDOR
	// ================================
	public ResponseCommon crearProveedor(ProveedorRequest proveedor) throws Exception {
		String jsonBody = mapper.writeValueAsString(proveedor);
	    String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/proveedor/crear", jsonBody, false);
	    System.out.println(response);
	    return mapper.readValue(response, ResponseCommon.class);
	}
	
	// ================================
	//      EDITAR PROVEEDOR
	// ================================
	public ResponseCommon editarProveedor(Long nit, ProveedorRequest proveedor) throws Exception {
	    String jsonBody = mapper.writeValueAsString(proveedor);
	    String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/proveedor/editar/" + nit, jsonBody);
	    return mapper.readValue(response, ResponseCommon.class);
	}
	
	// ================================
	//    OBTENER PROVEEDOR POR NIT
	// ================================
	public Proveedor obtenerProveedor(Long nit) throws Exception {
	    String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/proveedor/" + nit, false);
	    return mapper.readValue(response, Proveedor.class); 
	}
	
	// ================================
	//    ELIMINAR PROVEEDOR
	// ================================
	public ResponseCommon eliminarProveedor(Long nit) throws Exception {
	    String response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/proveedor/" + nit);
	    System.out.println(response);
	    return mapper.readValue(response, ResponseCommon.class); 
	}


	

}
