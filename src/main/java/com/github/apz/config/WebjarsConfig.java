package com.github.apz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@Component
@ConfigurationProperties(prefix = "webjars")
@Data
public class WebjarsConfig {

	String bootstrap;
	String jquery;

}
