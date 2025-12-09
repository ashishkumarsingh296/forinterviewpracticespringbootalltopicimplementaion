package com.example.forinterviewpracticespringbootalltopicimplementaion.sharding;

import com.example.forinterviewpracticespringbootalltopicimplementaion.sharding.ShardRoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.*;

@Configuration
@EnableTransactionManagement
public class ShardingDataSourceConfig {

    private final Environment env;

    public ShardingDataSourceConfig(Environment env) {
        this.env = env;
    }

    // create datasource for shard0
    @Bean(name = "shard0DataSource")
    public DataSource shard0DataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("spring.datasource.shard0.url"));
        ds.setUsername(env.getProperty("spring.datasource.shard0.username"));
        ds.setPassword(env.getProperty("spring.datasource.shard0.password"));
        return ds;
    }

    // create datasource for shard1
    @Bean(name = "shard1DataSource")
    public DataSource shard1DataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("spring.datasource.shard1.url"));
        ds.setUsername(env.getProperty("spring.datasource.shard1.username"));
        ds.setPassword(env.getProperty("spring.datasource.shard1.password"));
        return ds;
    }

    // optional shard2
    @Bean(name = "shard2DataSource")
    public DataSource shard2DataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("spring.datasource.shard2.url"));
        ds.setUsername(env.getProperty("spring.datasource.shard2.username"));
        ds.setPassword(env.getProperty("spring.datasource.shard2.password"));
        return ds;
    }

    // routing datasource
    @Bean(name = "dataSource")
    public DataSource routingDataSource(
            @Qualifier("shard0DataSource") DataSource shard0,
            @Qualifier("shard1DataSource") DataSource shard1,
            @Qualifier("shard2DataSource") DataSource shard2) {

        ShardRoutingDataSource routing = new ShardRoutingDataSource();
        Map<Object, Object> targetMap = new HashMap<>();
        targetMap.put("shard0", shard0);
        targetMap.put("shard1", shard1);
        targetMap.put("shard2", shard2);
        routing.setTargetDataSources(targetMap);
        routing.setDefaultTargetDataSource(shard0);
        return routing;
    }

    // EntityManagerFactory using routing DataSource
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {

        Map<String, Object> props = new HashMap<>();
        // copy common JPA props if present
        props.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
        props.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example.forinterviewpracticespringbootalltopicimplementaion"); // adjust package
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(props);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf.getObject());
        return tm;
    }
}
