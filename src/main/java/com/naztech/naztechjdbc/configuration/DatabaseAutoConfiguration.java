package com.naztech.naztechjdbc.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@Configuration
@ConditionalOnClass(DataSource.class)
public class DatabaseAutoConfiguration {

	
}
