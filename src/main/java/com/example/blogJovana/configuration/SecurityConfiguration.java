package com.example.blogJovana.configuration;

import com.example.blogJovana.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain customFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/register-admin").permitAll()
                        //.requestMatchers("/api/v1/auth/logout").authenticated()
                        .requestMatchers(HttpMethod.GET, "/posts/**", "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority(User.Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority(User.Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority(User.Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/posts/**").hasAnyAuthority(User.Role.ROLE_USER.name(), User.Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/posts/**").hasAnyAuthority(User.Role.ROLE_USER.name(), User.Role.ROLE_ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
