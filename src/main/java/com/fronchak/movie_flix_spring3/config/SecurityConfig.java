package com.fronchak.movie_flix_spring3.config;

import java.net.http.HttpRequest;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String USER = "USER";
	private static final String WORKER = "WORKER";
	private static final String ADMIN = "ADMIN";
	
	private static final String[] entities = { "/movies/**", "/genres/**" };

	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.headers().frameOptions().disable();
		http.cors().and().csrf().disable();
		http.authorizeHttpRequests()
			.requestMatchers("/auth/registerAux").hasRole(ADMIN)
			.requestMatchers("/auth/**").permitAll()
			.requestMatchers(HttpMethod.DELETE, entities).hasRole(ADMIN)
			.requestMatchers(HttpMethod.POST, entities).hasAnyRole(WORKER, ADMIN)
			.requestMatchers(HttpMethod.PUT, entities).hasAnyRole(WORKER, ADMIN)
			.requestMatchers(HttpMethod.GET, "/movies/genre").permitAll()
			.requestMatchers(HttpMethod.GET, "/movies/{id}", "/genres/{id}").authenticated()
			.requestMatchers(HttpMethod.GET, entities).permitAll();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authenticationProvider(authenticationProvider);
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
