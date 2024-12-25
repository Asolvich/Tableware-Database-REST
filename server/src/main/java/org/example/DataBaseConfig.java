package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Конфигурация базы данных с использованием Spring.
 * Этот класс используется для настройки подключения к базе данных.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class DataBaseConfig {
    /** Имя драйвера базы данных */
    @Value("${dataSource.driverClassName}")
    private String driverClassName;

    /** URL базы данных */
    @Value("${dataSource.url}")
    private String url;

    /** Имя пользователя для базы данных */
    @Value("${dataSource.username}")
    private String username;

    /** Пароль для базы данных */
    @Value("${dataSource.password}")
    private String password;

    /**
     * Создает источник данных для подключения к базе данных.
     * @return объект DataSource для подключения.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Создает JdbcTemplate для работы с базой данных.
     * @param dataSource источник данных для работы с базой.
     * @return JdbcTemplate для выполнения SQL-запросов.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
