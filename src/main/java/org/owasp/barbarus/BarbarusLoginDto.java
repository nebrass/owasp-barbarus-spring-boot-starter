package org.owasp.barbarus;

import java.io.Serializable;

/**
 *
 * @author nebrass
 */
public class BarbarusLoginDto implements Serializable {
    private String username;
    private String password;
    private String viewId;

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
