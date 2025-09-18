package application.utils;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientUtil {
	private static final HttpClient client = HttpClient.newHttpClient();
	
	public static String get(String url) throws Exception{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(url))
				.GET()
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		return response.body();
	}

	 public static String post(String url, String jsonBody) throws Exception {
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(new URI(url))
	                .header("Content-Type", "application/json")
	                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
	                .build();

	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        return response.body();
	    }
}
