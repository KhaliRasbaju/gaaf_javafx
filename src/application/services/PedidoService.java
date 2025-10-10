<<<<<<< HEAD
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.PedidoRequest;
import application.models.response.Pedido;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class PedidoService {
	private final ObjectMapper mapper = new ObjectMapper();

    public List<Pedido> obtenerPedidos() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido");
        return mapper.readValue(response, new TypeReference<List<Pedido>>() {});
    }

    public Pedido crearPedido(PedidoRequest pedido) throws Exception {
        String jsonBody = mapper.writeValueAsString(pedido);
        String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/pedido/crear", jsonBody);
        return mapper.readValue(response, Pedido.class);
    }

    public Pedido recibirPedido(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido/recibir/" + id);
        return mapper.readValue(response, Pedido.class);
    }
    
    public Pedido obtenerPedido(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido/" + id);
        return mapper.readValue(response, Pedido.class);
    }

    public ResponseCommon eliminarPedido(Long id) throws Exception {
        var response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/pedido/" + id);
        return mapper.readValue(response, ResponseCommon.class);
    }
}
=======
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.request.PedidoRequest;
import application.models.response.Pedido;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class PedidoService {
	private final ObjectMapper mapper = new ObjectMapper();

    public List<Pedido> obtenerPedidos() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido");
        return mapper.readValue(response, new TypeReference<List<Pedido>>() {});
    }

    public Pedido crearPedido(PedidoRequest pedido) throws Exception {
        String jsonBody = mapper.writeValueAsString(pedido);
        String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/pedido/crear", jsonBody);
        return mapper.readValue(response, Pedido.class);
    }

    public Pedido recibirPedido(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido/recibir/" + id);
        return mapper.readValue(response, Pedido.class);
    }
    
    public Pedido obtenerPedido(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido/" + id);
        return mapper.readValue(response, Pedido.class);
    }

    public ResponseCommon eliminarPedido(Long id) throws Exception {
        var response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/pedido/" + id);
        return mapper.readValue(response, ResponseCommon.class);
    }
}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
