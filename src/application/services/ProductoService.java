package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.ProductoRequest;
import application.models.response.Producto;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class ProductoService {
	
	private final ObjectMapper mapper = new ObjectMapper();
	

	
	public ResponseCommon crearProducto(ProductoRequest productoRequest) throws Exception {
		String json = mapper.writeValueAsString(productoRequest);
		String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/producto/crear", json, false);
		System.out.println(response);
		return mapper.readValue(response, ResponseCommon.class);	
	}
	
	public ResponseCommon editarProducto(ProductoRequest productoRequest, Long id) throws Exception {
		String json = mapper.writeValueAsString(productoRequest);
		String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/producto/editar/"+id, json);
		System.out.println(response);
		return mapper.readValue(response, ResponseCommon.class);	
	}

	public Producto obtenerProducto(Long id) throws Exception{
		String response = HttpClientUtil.get(ApiConfig.BASE_URL+"/producto/"+id, false);
		return mapper.readValue(response, Producto.class);
	}
	
	public List<Producto> obtenerProductos() throws Exception{
		String response = HttpClientUtil.get(ApiConfig.BASE_URL+"/producto", false);
		return mapper.readValue(response, new TypeReference<List<Producto>>() {});
	}
	
	public ResponseCommon eliminarProducto(Long id) throws Exception {
		String response = HttpClientUtil.delete(ApiConfig.BASE_URL+"/producto/"+id.toString());
		return mapper.readValue(response, ResponseCommon.class);
	}

	

	
}
