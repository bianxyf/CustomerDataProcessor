package test;

import main.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {
    @Mock
    HttpResponse<InputStream> mockResponse;

    @Spy
    HttpClient httpClient;

    @Test
    public void shouldVerifyCallCustomerAPI() throws IOException, InterruptedException {
        String jsonRequest = " {\"customerReference\":\"12357\",\"firstName\":\"JJ\",\"lastName\":\"Dee\",\"addressLine1\":\"27 Forest Road\",\"addressLine2\":\"Stretford\",\"town\":\"Greater Manchester\",\"county\":\"Manchester\",\"country\":\"United Kingdom\",\"postCode\":\"M32 8TP\"}";
        HttpResponse<String> returnedResponse = Client.callCustomerAPI(jsonRequest);

        HttpResponse mockResponse = mock(HttpResponse.class);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\": 3}");

        assertNotNull(returnedResponse.body());
        assertEquals(mockResponse.body(), returnedResponse.body());

    }

}
