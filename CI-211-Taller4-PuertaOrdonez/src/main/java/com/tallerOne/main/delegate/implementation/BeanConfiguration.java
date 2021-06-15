package com.tallerOne.main.delegate.implementation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

	@Bean
	RestTemplate template() {return new RestTemplate();};
	
}
