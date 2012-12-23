package com.dominiak.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.inject.Inject;

@Configuration
@Profile("mysql")
@ComponentScan(basePackages = {"com.dominiak.config"})
public class MysqlConfiguration implements DatabaseConfiguration {

    @Inject
    Environment env;

    @Bean(destroyMethod = "close")
    public BasicDataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(env.getProperty("mysql.jdbc.url" ,"jdbc:mysql://localhost:3306/nontransactionaltests"));
        ds.setUsername(env.getProperty("mysql.jdbc.username" , "root"));
        ds.setPassword(env.getProperty("mysql.jdbc.password" , ""));
        return ds;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(MySQL5Dialect.class.getName());
        return vendorAdapter;
    }
}
