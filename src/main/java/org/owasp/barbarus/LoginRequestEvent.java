package org.owasp.barbarus;

import org.springframework.context.ApplicationEvent;

public class LoginRequestEvent extends ApplicationEvent {
    private BarbarusLoginDto content;

    public LoginRequestEvent(Object source, BarbarusLoginDto content) {
        super(source);
        this.content = content;
    }

    public BarbarusLoginDto getContent() {
        return content;
    }
}
