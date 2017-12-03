package com.zorro.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages="com.zorro.backend.persistence.repositories")
@EntityScan(basePackages="com.zorro.backend.persistence.domain.backend")
@EnableTransactionManagement
@PropertySource("classpath:/application-common.properties")
public class ApplicationConfig {

}
