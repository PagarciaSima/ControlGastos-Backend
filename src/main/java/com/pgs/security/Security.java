package com.pgs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pgs.jwt.JwtAuthFilter;
import com.pgs.jwt.UserInfoService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Security {

	
	@Autowired
	private JwtAuthFilter authFilter;

    @Bean
    UserDetailsService userDetailsService() {
		return new UserInfoService();
	}

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authenticationProvider;
	}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception 
	{
		return authenticationConfiguration.getAuthenticationManager();
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	            .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF (si es necesario)
	            .authorizeHttpRequests(authz -> authz
	                .requestMatchers("/api/auth/**").permitAll()  // Rutas públicas (login, registro, etc.)
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/actuator/**").permitAll()  // Permite Swagger y OpenAPI sin autenticación
	                .requestMatchers("/api/v1/**").authenticated()  // Rutas protegidas
	            )
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Sesiones sin estado
	            )
	            .exceptionHandling(exceptions -> exceptions
	                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))  // Manejo de errores
	            )
	            .authenticationProvider(authenticationProvider())  // Proveedor de autenticación
	            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)  // Filtro de autenticación
	            .build();
	    
	    
	}

	

}
