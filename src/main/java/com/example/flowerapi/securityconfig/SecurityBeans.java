package com.example.flowerapi.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(basic -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/flower/add","/stock/add")
                        .hasAuthority("Role_Admin")
                        .requestMatchers(HttpMethod.GET, "/stock/getData")
                        .hasAuthority("Role_Admin")
                        .requestMatchers(HttpMethod.DELETE, "/flower/delete/**", "/stock/delete/**")
                        .hasAuthority("Role_Admin")
                        .requestMatchers(HttpMethod.PATCH, "/flower/update/**")
                        .hasAuthority("Role_Admin")
                        .requestMatchers(HttpMethod.PUT, "/flower/update/**", "/stock/update/**")
                        .hasAuthority("Role_Admin")

                        .requestMatchers(HttpMethod.POST, "/cart/add","/checkout/**")
                        .hasAnyAuthority("Role_Admin", "Role_User")
                        .requestMatchers(HttpMethod.GET, "/cart/findAll")
                        .hasAnyAuthority("Role_Admin", "Role_User")
                        .requestMatchers(HttpMethod.DELETE, "/cart/delete/**")
                        .hasAnyAuthority("Role_Admin", "Role_User")
                        .requestMatchers(HttpMethod.PATCH,"/cart/update/**")
                        .hasAnyAuthority("Role_Admin", "Role_User")
                        .requestMatchers(HttpMethod.GET, "/flower/flowers","/flower/flower/**", "/flower/filter/**", "/flower/**")
                        .hasAnyAuthority("Role_Admin","Role_User")

                        .requestMatchers(HttpMethod.POST,"/auth/register", "/auth/login")
                        .permitAll()

                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
