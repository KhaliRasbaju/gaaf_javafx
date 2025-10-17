package application.utils;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import application.session.SessionManager;

public class HttpClientUtil {
	private static final HttpClient client = HttpClient.newHttpClient();
	
	private static String getToken() {
	    return SessionManager.getInstance().getToken();
	}
	public static String get(String url, boolean noHeader) throws Exception{
		System.out.println(getToken());
		HttpRequest.Builder builder = HttpRequest.newBuilder()
				.uri(new URI(url))
				.GET();

	    if (!noHeader && getToken() != null) {
	    	builder.header("Authorization", "Bearer " + getToken());
	    }
	    HttpRequest request = builder.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		return response.body();
	}

	 public static String post(String url, String jsonBody, Boolean noHeader) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
        if (!noHeader && getToken() != null) {
	    	builder.header("Authorization", "Bearer " + getToken());
	    }
        
        HttpRequest request = builder.build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
	 
	 public static String put(String url, String jsonBody) throws Exception {
		 HttpRequest request = HttpRequest.newBuilder()
				 .uri(new URI(url))
				 .header("Authorization", "Bearer " + getToken())
				 .header("Content-Type", "application/json")
				 .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
				 .build();
		 
		 
		 HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		 System.out.println(response);
        return response.body();
	 }
	 

	 
	 public static String delete(String url) throws Exception {
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(new URI(url))
	            .header("Authorization", "Bearer " + getToken())
	            .header("Accept", "application/json")
	            .DELETE()
	            .build();

	    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    return response.body();
	 }
}
