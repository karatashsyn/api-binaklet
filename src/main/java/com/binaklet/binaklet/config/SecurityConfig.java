package com.binaklet.binaklet.config;


import com.binaklet.binaklet.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()


                .requestMatchers(HttpMethod.GET,"/api/items/**","/api/users/**", "/api/transporters/").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()

                //TODO:ADMIN FILTRESI CALISMIYOR, bunu sil
                //TODO:ADMIN FILTRESI CALISMIYOR, bunu sil
                //TODO:ADMIN FILTRESI CALISMIYOR, bunu sil
                .requestMatchers("/api/admin/items").permitAll()
                .requestMatchers("/api/admin/users").permitAll()
                //TODO:ADMIN FILTRESI CALISMIYOR, bunu sil
                //TODO:ADMIN FILTRESI CALISMIYOR, bunu sil
                //TODO:ADMIN FILTRESI CALISMIYOR, bunu sil


                .anyRequest().authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);


        return http.build();



    }
}