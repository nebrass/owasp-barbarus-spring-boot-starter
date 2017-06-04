package org.owasp.barbarus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nebrass
 */
//TODO
public class BarbarusLoginDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Size(min = 36, max = 36)
    private String viewId;

    public BarbarusLoginDto() {
        // Empty constructor needed for Jackson.
    }

    public BarbarusLoginDto(String username, String password, String viewId) {
        this.username = username;
        this.password = password;
        this.viewId = viewId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    @Override
    public String toString() {
        return "BarbarusLoginDto{" + "username=" + username + ", password=" + password.replaceAll("(.*)*", "*") + ", viewId=" + viewId + '}';
    }
}
