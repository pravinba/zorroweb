package com.zorro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.zorro.backend.service.EmailService;
import com.zorro.backend.service.smtpEmailService;

@Configuration
@Profile("prod")
@PropertySource("classpath:/application-prod.properties")
public class ProductionConfig {
	
	@Bean
    public EmailService emailService() {
        return new smtpEmailService();
    }
}
