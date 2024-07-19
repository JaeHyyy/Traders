package com.exam.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityFilterChainConfig {

	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
	        
	        // https://github.com/spring-projects/spring-security/issues/12310 참조
	        return httpSecurity
	                .authorizeHttpRequests(auth -> 
	                
	                auth.antMatchers("/","/users","/hello").permitAll()  // 회원가입 요청 허용.
	                    .antMatchers("/authenticate").permitAll()
//	                    .antMatchers(PathRequest.toH2Console()).permitAll() // h2 사용시 h2-console 허용하기 위한 처리.
	                    .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
	                    .anyRequest()
	                    .authenticated())
	                .csrf(AbstractHttpConfigurer::disable)
	                .sessionManagement(session -> session.
	                    sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .oauth2ResourceServer(
	                        OAuth2ResourceServerConfigurer::jwt)
	                .httpBasic(
	                        Customizer.withDefaults())
	                .headers(header -> {header.
	                    frameOptions().sameOrigin();})
	                .build();
	    }
//
	
	  
	    @Bean
	    public JWKSource<SecurityContext> jwkSource() {
	        JWKSet jwkSet = new JWKSet(rsaKey());
	        return (((jwkSelector, securityContext) 
	                        -> jwkSelector.select(jwkSet)));
	    }

	    @Bean
	    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
	        return new NimbusJwtEncoder(jwkSource);
	    }

	    @Bean
	    JwtDecoder jwtDecoder() throws JOSEException {
	        return NimbusJwtDecoder
	                .withPublicKey(rsaKey().toRSAPublicKey())
	                .build();
	    }
	    
	    @Bean
	    public RSAKey rsaKey() {
	        
	        KeyPair keyPair = keyPair();
	        
	        return new RSAKey
	                .Builder((RSAPublicKey) keyPair.getPublic())
	                .privateKey((RSAPrivateKey) keyPair.getPrivate())
	                .keyID(UUID.randomUUID().toString())
	                .build();
	    }

	    @Bean
	    public KeyPair keyPair() {
	        try {
	            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	            keyPairGenerator.initialize(2048);
	            return keyPairGenerator.generateKeyPair();
	        } catch (Exception e) {
	            throw new IllegalStateException(
	                    "Unable to generate an RSA Key Pair", e);
	        }
	    }
}
