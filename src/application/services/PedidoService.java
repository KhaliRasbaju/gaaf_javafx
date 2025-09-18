package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.Pedido;
import application.utils.HttpClientUtil;

public class PedidoService {
	public List<Pedido> getPedidos() throws Exception {
		String response = HttpClientUtil.get(ApiConfig.BASE_URL+"/pedido");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response, new TypeReference<List<Pedido>>() {});
		
	}
}
