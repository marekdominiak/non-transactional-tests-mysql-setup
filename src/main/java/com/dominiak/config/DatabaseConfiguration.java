package com.dominiak.config;

import org.springframework.orm.jpa.JpaVendorAdapter;

import javax.sql.DataSource;


public interface DatabaseConfiguration {
    DataSource dataSource();

    JpaVendorAdapter jpaVendorAdapter();
}
