package org.owasp.barbarus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventsPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventsPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final BarbarusLoginDto loginRequest) {
        LoginRequestEvent loginRequestEvent = new LoginRequestEvent(this, loginRequest);
        applicationEventPublisher.publishEvent(loginRequestEvent);
    }
}
