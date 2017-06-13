package org.owasp.barbarus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;

@Configuration
@PropertySource("classpath:application-test.properties")
public class ApplicationConfiguration {

    @Bean
    public AuthenticationManager authenticationManager() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        User basicUser =
                new User("user", "password", Collections.emptyList());

        User managerUser =
                new User("admin", "password", Collections.emptyList());

        return new InMemoryUserDetailsManager(Arrays.asList(
                basicUser, managerUser
        ));
    }

}
