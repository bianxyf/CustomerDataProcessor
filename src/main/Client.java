package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static HttpResponse<String> callCustomerAPI(String jsonRequest){
        LOGGER.info(jsonRequest);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/saveCustomerDetail"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            LOGGER.severe("IOException Encountered while reading the file");
        } catch (InterruptedException e) {
            LOGGER.severe("InterruptedException Encountered while reading the file");
        }
        LOGGER.info(response.body());
        return response;
    }
}
