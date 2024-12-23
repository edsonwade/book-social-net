package code.with.vanilson.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@SuppressWarnings("all")
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain responsible for authenticating and authorizing users.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(withDefaults()) // to enable CORS
                .csrf(csrf -> csrf.disable()) // to disable CSRF
                .authorizeHttpRequests(req -> req
                        .requestMatchers(
                                "/auth/**",
                                "/v2/api/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/actuator/**",
                                "/management/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-ui/index.html",
                                "/webjars/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/swagger-resources/configuration/ui",
                                "/swagger-resources/configuration/security",
                                "/configuration/ui",
                                "/configuration/security")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // to
                // enable stateless session, will not store session information in the server.
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
