package com.fansipan.test.cs2017.silverbar.config;

import com.fansipan.test.cs2017.silverbar.dao.InMemoryOrderDao;
import com.fansipan.test.cs2017.silverbar.dao.OrderDao;
import com.fansipan.test.cs2017.silverbar.rest.OrderRest;
import io.swagger.jaxrs.config.BeanConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value="classpath:service.properties", ignoreResourceNotFound=true)
public class ServiceConfig {
    @Value("${orders.initialSize:1000}")
    private int ordersInitialSize;

    @Value("${service.version}")
    private String serviceVersion;

    @Value("${service.basePath}")
    private String serviceBasePath;

    @Value("${service.title}")
    private String serviceTitle;

    @Value("${service.description}")
    private String serviceDescription;

    @Value("${service.contact}")
    private String serviceContact;

    @Bean
    public OrderDao orderDao() {
        return new InMemoryOrderDao(ordersInitialSize);
    }


    @Bean
    public OrderRest orderRest() {
        return new OrderRest(orderDao());
    }


    @Bean
    public BeanConfig beanConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(serviceVersion);
        beanConfig.setBasePath(serviceBasePath);
        beanConfig.setTitle( serviceTitle);
        beanConfig.setDescription( serviceDescription );
        beanConfig.setContact(serviceContact);
        beanConfig.setResourcePackage("com.fansipan.test.cs2017.silverbar.rest");
        beanConfig.setScan( true );

        return beanConfig;
    }
}
