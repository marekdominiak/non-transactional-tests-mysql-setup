package com.dominiak;

import com.dominiak.config.DatabaseConfiguration;
import com.dominiak.config.H2Configuration;
import com.dominiak.config.MysqlConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.dominiak"})
@ImportResource("classpath:/application-context.xml")
@Import({H2Configuration.class, MysqlConfiguration.class})
public class AppContext {

    public EntityManagerFactory entityManagerFactory(DatabaseConfiguration databaseConfiguration) {
        return entityManagerFactoryBean(databaseConfiguration).getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager(DatabaseConfiguration databaseConfiguration) {
        return new JpaTransactionManager(entityManagerFactory(databaseConfiguration));
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DatabaseConfiguration databaseConfiguration) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(databaseConfiguration.dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(databaseConfiguration.jpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.dominiak");

        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
        entityManagerFactoryBean.setJpaProperties(properties);
        return entityManagerFactoryBean;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }
}
