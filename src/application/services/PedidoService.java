package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import application.config.ApiConfig;
import application.models.request.PedidoRequest;
import application.models.response.Pedido;
import application.models.response.ResponseCommon;
import application.utils.HttpClientUtil;

public class PedidoService {
	
    // ================================
    //        MAPEO DEL JSON
    // ================================
	private final ObjectMapper mapper = new ObjectMapper();
	
	// ================================
	//        CONSTRUCTOR
	// ================================
    public PedidoService() {
    	mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	// ================================
	//       OBTENER PEDIDOS
	// ================================
	public List<Pedido> obtenerPedidos() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido", false);
        return mapper.readValue(response, new TypeReference<List<Pedido>>() {});
    }

	// ================================
	//       CREAR PEDIDO
	// ================================
    public ResponseCommon crearPedido(PedidoRequest pedido) throws Exception {
        String jsonBody = mapper.writeValueAsString(pedido);
        String response = HttpClientUtil.post(ApiConfig.BASE_URL + "/pedido/crear", jsonBody, false);
        return mapper.readValue(response, ResponseCommon.class);
    }

    // ================================
    //       RECIBIR PEDIDO
    // ================================
    public ResponseCommon recibirPedido(Long id) throws Exception {
        String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/pedido/recibir/" + id);
        return mapper.readValue(response, ResponseCommon.class);
    }
    
    // ================================
    //       OBTENER PEDIDO POR ID
    // ================================
    public Pedido obtenerPedido(Long id) throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/pedido/" + id, false);
        return mapper.readValue(response, Pedido.class);
    }

    // ================================
    //       ELIMINAR PEDIDO
    // ================================
    public ResponseCommon eliminarPedido(Long id) throws Exception {
        var response = HttpClientUtil.delete(ApiConfig.BASE_URL + "/pedido/" + id);
        return mapper.readValue(response, ResponseCommon.class);
    }
    
    // ================================
    //       EDITAR PEDIDO
    // ================================
    public ResponseCommon editarPedido(Long id, PedidoRequest pedido) throws Exception {
        String jsonBody = mapper.writeValueAsString(pedido);
        String response = HttpClientUtil.put(ApiConfig.BASE_URL + "/pedido/editar/" + id, jsonBody);
        return mapper.readValue(response, ResponseCommon.class);
    }

}
