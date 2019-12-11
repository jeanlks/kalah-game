package com.test.bol.boltest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
@EnableWebSocketMessageBroker
public class BolTestApplication implements WebSocketMessageBrokerConfigurer {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
	  config.enableSimpleBroker("/topic");
	  config.setApplicationDestinationPrefixes("/app");
	}
  
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	  registry.addEndpoint("/moves")
				  .setAllowedOrigins("*")
				  .withSockJS();
	}

	public static void main(String[] args) {
		SpringApplication.run(BolTestApplication.class, args);
	}

}
