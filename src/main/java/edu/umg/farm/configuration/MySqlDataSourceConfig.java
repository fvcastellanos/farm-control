package edu.umg.farm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MySqlDataSourceConfig {

    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String database;

    public MySqlDataSourceConfig(@Value("${datasource.mysql.host:127.0.0.1}") final String host,
                                 @Value("${datasource.mysql.port:3306}") final String port,
                                 @Value("${datasource.mysql.username:farm_control}") final String username,
                                 @Value("${datasource.mysql.password:control123}") final String password,
                                 @Value("${datasource.mysql.database:farm_control}") final String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    @Bean
    public DataSource mySqlDataSource(@Value("${spring.datasource.driverClassName:com.mysql.jdbc.Driver}") final String driverClassName) {
        final var jdbcUrl = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
        final var hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(this.username);
        hikariConfig.setPassword(this.password);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource mySqlDataSource) {
        return new DataSourceTransactionManager(mySqlDataSource);
    }
}
