package com.emp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.emp.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	private final JwtAuthenticationFilter authenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter authenticationFilter) {
		this.authenticationFilter = authenticationFilter;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder) {
		
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		
		return provider;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity.addFilterBefore(
        		authenticationFilter,
                UsernamePasswordAuthenticationFilter.class
 )
        
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

            	    .requestMatchers("/user/login", "/user/register").permitAll()

            	    .requestMatchers(
            	        "/user/all",
            	        "/user/update/**",
            	        "/user/delete/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers("/user/profile/**").authenticated()

            	    .requestMatchers(
            	        "/dept/save",
            	        "/dept/update/**",
            	        "/dept/delete/**"
            	    ).hasRole("ADMIN")
            	    .requestMatchers(
            	        "/dept/get/**",
            	        "/dept/all"
            	    ).authenticated()
            	    
            	    .requestMatchers(
            	    	    "/employee/save",
            	    	    "/employee/get/**",
            	    	    "/employee/all",
            	    	    "/employee/update/**",
            	    	    "/employee/delete/**"
            	    	).hasRole("ADMIN")
            	    
            	    .requestMatchers("/employee/profile/**").authenticated()

            	    .anyRequest().authenticated()
            	);

        return httpSecurity.build();
    }
}