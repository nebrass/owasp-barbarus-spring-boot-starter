package org.owasp.barbarus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties
public class BarbarusProperties {
    @NotNull
    @Value("${barbarus.paramters.view-id}")
    private String viewId;
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

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public long getTokenValidityInMillis() {
        return tokenValidityInMillis;
    }

    public void setTokenValidityInMillis(long tokenValidityInMillis) {
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
}
