package org.example.bridge.rpa.uipath.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class AuthTokenCloud implements AuthToken {
    String accessToken;
    String idToken;
    String scope;
    long expiresIn;
    String tokenType;

    public AuthTokenCloud() {
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIdToken() {
        return this.idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String toString() {
        return "AuthToken [accessToken=" + this.accessToken + ", idToken=" + this.idToken + ", scope=" + this.scope + ", expiresIn=" + this.expiresIn + ", tokenType=" + this.tokenType + "]";
    }
}