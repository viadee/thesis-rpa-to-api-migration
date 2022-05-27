package org.example.bridge.rpa;


import java.io.IOException;
import java.util.function.Function;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.camunda.commons.utils.IoUtil;
import org.camunda.spin.Spin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);

    public RequestHandler() {
    }

    public static <T> T sendRequest(HttpUriRequest request, HttpClient httpClient, int expectedStatus, Class<T> responseType) throws IOException {
        return sendRequest(request, httpClient, expectedStatus, (s) -> {
            return "Unexpected response from UiPath: " + s;
        }, responseType);
    }

    public static <T> T sendRequest(HttpUriRequest request, HttpClient httpClient, int expectedStatus, Function<String, String> errorMessageHandler, Class<T> responseType) throws IOException {
        HttpResponse response = httpClient.execute(request);
        String payload = IoUtil.inputStreamAsString(response.getEntity().getContent());
        LOG.debug("Received the following response payload for request {}: {}", request.getURI(), payload);
        if (response.getStatusLine().getStatusCode() != expectedStatus) {
            throw new IllegalStateException((String)errorMessageHandler.apply(payload));
        } else {
            return Spin.JSON(payload).mapTo(responseType);
        }
    }
}
