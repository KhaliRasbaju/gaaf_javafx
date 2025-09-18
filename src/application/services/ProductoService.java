package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.Producto;

import application.utils.HttpClientUtil;

public class ProductoService {
	public List<Producto> getProducts() throws Exception{
		String response = HttpClientUtil.get(ApiConfig.BASE_URL+"/producto");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response, new TypeReference<List<Producto>>() {});
	}
}
