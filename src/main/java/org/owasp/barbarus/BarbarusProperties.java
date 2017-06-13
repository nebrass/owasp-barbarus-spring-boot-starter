package org.owasp.barbarus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties
public class BarbarusProperties {
    @Value("${barbarus.paramters.view-id}")
    private String viewId = "viewId";
    @Value("${barbarus.paramters.username}")
    private String username = "username";
    @Value("${barbarus.paramters.password}")
    private String pass = "password";
    @NotNull
    @Value("${barbarus.paramters.jwt-token}")
    private String jwtToken;
    @Value("${barbarus.paramters.token-validity-in-millis}")
    private Long tokenValidityInMillis = 86400L;
    @NotNull
    @Value("${barbarus.paramters.jwt-secret-key}")
    private String jwtSecretKey;
    @Value("${barbarus.paramters.authorities-key}")
    private String authoritiesKey = "auth";
    @Value("${barbarus.controller.sse-emitter-timeout}")
    private Long sseEmitterTimeout = 60000L;

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Long getTokenValidityInMillis() {
        return tokenValidityInMillis;
    }

    public void setTokenValidityInMillis(Long tokenValidityInMillis) {
        this.tokenValidityInMillis = tokenValidityInMillis;
    }

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String getAuthoritiesKey() {
        return authoritiesKey;
    }

    public void setAuthoritiesKey(String authoritiesKey) {
        this.authoritiesKey = authoritiesKey;
    }

    public Long getSseEmitterTimeout() {
        return sseEmitterTimeout;
    }

    public void setSseEmitterTimeout(Long sseEmitterTimeout) {
        this.sseEmitterTimeout = sseEmitterTimeout;
    }
}
