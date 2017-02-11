package com.fansipan.test.cs2017.silverbar.config;

import com.fansipan.test.cs2017.silverbar.dao.OrderDao;
import com.fansipan.test.cs2017.silverbar.rest.OrderRest;
import io.swagger.jaxrs.config.BeanConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class ServiceConfigTest {
    @Test
    public void testBeans() throws Exception {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
        assertNotNull(appContext);
        assertNotNull(appContext.getBean(OrderDao.class));
        assertNotNull(appContext.getBean(OrderRest.class));
        assertNotNull(appContext.getBean(BeanConfig.class));
    }

}