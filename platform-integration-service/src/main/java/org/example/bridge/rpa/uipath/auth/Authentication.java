package org.example.bridge.rpa.uipath.auth;

import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.camunda.spin.Spin;
import org.example.bridge.rpa.RequestHandler;
import org.example.bridge.properties.BridgeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

public class Authentication {
    private static final Logger LOG = LoggerFactory.getLogger(Authentication.class);
    @Autowired
    BridgeProperties properties;
    @Autowired
    HttpClient httpClient;

    public Authentication() {
    }

    public AuthToken getToken() throws IOException {
        BridgeProperties uipathApiProperties = this.properties;
        if (uipathApiProperties != null && StringUtils.hasText(uipathApiProperties.getTenantName()) && StringUtils.hasText(uipathApiProperties.getAuthUrl()) && StringUtils.hasText(uipathApiProperties.getKey()) && StringUtils.hasText(uipathApiProperties.getUser())) {

            String credentialsString = null;
            credentialsString = Spin.JSON(new AuthTokenCredentialsCloud(uipathApiProperties.getUser(), uipathApiProperties.getKey().toString())).toString();

            StringEntity json = new StringEntity(credentialsString);
            json.setContentType("application/json");
            String url = uipathApiProperties.getAuthUrl();
            LOG.debug("Sending request to UiPath for authentication with URL {} and payload {}", url, credentialsString);
            HttpUriRequest getTokenRequest = RequestBuilder.post(url).addHeader("X-UIPATH-TenantName", uipathApiProperties.getTenantName()).setEntity(json).build();
            Class<? extends AuthToken> responseType = AuthTokenCloud.class;
            return (AuthToken) RequestHandler.sendRequest(getTokenRequest, this.httpClient, 200, (s) -> {
                return "Error on authentication against UiPath with error: " + s;
            }, responseType);
        } else {
            throw new IllegalStateException("Application properties need to at least contain defined '.tenant-name' under 'org.camunda.bpm.rpa.uipath-api' as well as '.type', '.auth-url', '.user' and '.key' under 'org.camunda.bpm.rpa.uipath-api.authentication'. For UiPath Automation Cloud, a defined '.account-name' under 'org.camunda.bpm.rpa.uipath-api' is necessary as well.");
        }
    }
}
