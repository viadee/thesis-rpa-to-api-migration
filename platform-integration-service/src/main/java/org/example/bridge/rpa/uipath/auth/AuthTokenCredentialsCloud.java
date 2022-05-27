package org.example.bridge.rpa.uipath.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class AuthTokenCredentialsCloud {
    String grantType = "refresh_token";
    String clientId;
    String refreshToken;

    public AuthTokenCredentialsCloud(String clientId, String refreshToken) {
        this.clientId = clientId;
        this.refreshToken = refreshToken;
    }

    public String getGrantType() {
        return this.grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
