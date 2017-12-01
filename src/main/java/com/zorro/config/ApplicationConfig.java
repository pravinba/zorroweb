package com.zorro.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages="com.zorro.backend.persistence.repositories")
@EntityScan(basePackages="com.zorro.backend.persistence.domain.backend")

public class ApplicationConfig {

}
