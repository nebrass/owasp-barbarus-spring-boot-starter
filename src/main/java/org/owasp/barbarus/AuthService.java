package org.owasp.barbarus;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@EnableConfigurationProperties(BarbarusProperties.class)
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final BarbarusProperties barbarusProperties;

    public AuthService(AuthenticationManager authenticationManager, BarbarusProperties barbarusProperties) {
        this.authenticationManager = authenticationManager;
        this.barbarusProperties = barbarusProperties;
    }

    public String authorize(BarbarusLoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication =
                this
                        .authenticationManager
                        .authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.createToken(authentication);
    }

    private String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.barbarusProperties.getTokenValidityInMillis());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(this.barbarusProperties.getAuthoritiesKey(), authorities)
                .signWith(SignatureAlgorithm.HS512, this.barbarusProperties.getJwtSecretKey())
                .setExpiration(validity)
                .compact();
    }
}
