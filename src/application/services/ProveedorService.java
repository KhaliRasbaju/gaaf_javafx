package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.Proveedor;
import application.utils.HttpClientUtil;

public class ProveedorService {
	public List<Proveedor> getAll() throws Exception{
		String response = HttpClientUtil.get(ApiConfig.BASE_URL+"/proveedor");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response, new TypeReference<List<Proveedor>>() {});
	}

}
