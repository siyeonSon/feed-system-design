package com.demo.feedsystemdesign.infra.config.properties;

import com.demo.feedsystemdesign.infra.config.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(RedisProperties.class)
@Configuration
public class PropertiesConfig {}
