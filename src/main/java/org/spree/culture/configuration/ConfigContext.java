package org.spree.culture.configuration;

import org.spree.core.parameter.ConfigStorage;
import org.spree.core.parameter.JdbcConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ConfigContext {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public ConfigStorage configStorage() {
        return new JdbcConfigStorage(jdbcTemplate);
    }
}
