package com.zorro.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.zorro.backend.service.EmailService;
import com.zorro.backend.service.smtpEmailService;

@Configuration
@Profile("prod")
@PropertySource("classpath:/application-prod.properties")
@PropertySource("classpath:/stripe.properties")
public class ProductionConfig {
	
	@Value("${stripe.test.private.key}")	
	private String stripeProdKey;
	
	@Bean
    public EmailService emailService() {
        return new smtpEmailService();
    }
	
	@Bean
	public ServletRegistrationBean h2ConsoleServletRegistration(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet());
		bean.addUrlMappings("/console/*");		
		return bean;
	}
	
	@Bean
	public String stripeKey() {
		return stripeProdKey;
	}
}
