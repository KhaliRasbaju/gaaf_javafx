<<<<<<< HEAD
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.ReporteCompra;
import application.utils.HttpClientUtil;

public class ReporteCompraService {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<ReporteCompra> obtenerReporteCompras() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/reporte/compra");
        return mapper.readValue(response, new TypeReference<List<ReporteCompra>>(){});
    }


}
=======
package application.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.ApiConfig;
import application.models.response.ReporteCompra;
import application.utils.HttpClientUtil;

public class ReporteCompraService {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<ReporteCompra> obtenerReporteCompras() throws Exception {
        String response = HttpClientUtil.get(ApiConfig.BASE_URL + "/reporte/compra");
        return mapper.readValue(response, new TypeReference<List<ReporteCompra>>(){});
    }


}
>>>>>>> b614632 (Se cambiaron los estilos y se movieron hacia el css)
