package org.owasp.barbarus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
public class EventsListener implements ApplicationListener<LoginRequestEvent> {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(EventsListener.class.getName());

    private final BarbarusEndpoint barbarusEndpoint;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final BarbarusProperties barbarusProperties;

    public EventsListener(BarbarusEndpoint barbarusEndpoint,
                          PasswordEncoder passwordEncoder,
                          UserDetailsService userDetailsService,
                          AuthService authService,
                          BarbarusProperties barbarusProperties) {
        this.barbarusEndpoint = barbarusEndpoint;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
        this.barbarusProperties = barbarusProperties;
    }

    @Override
    public void onApplicationEvent(LoginRequestEvent event) {
        BarbarusLoginDto loginDto =
                event.getContent();

        SseEmitter sseEmitter
                = this.barbarusEndpoint
                .getEmitters()
                .get(loginDto.getViewId());
        try {
            handleSseResponse(sseEmitter, loginDto);
        } catch (IOException | UsernameNotFoundException e) {
            LOGGER.warn("Unable to handle response", e);
        }

        LOGGER.info("Received message as Authentication Request class: {}", loginDto);
    }

    private void handleSseResponse(SseEmitter sseEmitter,
                                   BarbarusLoginDto loginDto) throws IOException {
        if (sseEmitter != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDto.getUsername());
            boolean matches = this.passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword());
            SseResponseBuilder responseBuilder = new SseResponseBuilder();

            if (matches) {
                String jwtToken = this.authService.authorize(loginDto);
                responseBuilder.name(this.barbarusProperties.getJwtToken());
                responseBuilder.data(jwtToken);
                sseEmitter.send(responseBuilder);
                sseEmitter.complete();
            } else {
                responseBuilder.name("error");
                responseBuilder.data("BAD REQUEST -- UNAUTHORIZED");
                sseEmitter.send(responseBuilder);
            }
        }
    }
}
