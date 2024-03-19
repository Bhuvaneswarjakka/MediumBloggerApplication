package com.Bhuvaneswar.MediumBloggerApplication.Security;

import com.Bhuvaneswar.MediumBloggerApplication.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig
{

    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UserService userService;

    public AppSecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter, JWTService jwtService, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception
    {
        return new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, userService));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.POST).anonymous().requestMatchers("/users", "/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET).anonymous().requestMatchers("/articles", "/articles/*").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
