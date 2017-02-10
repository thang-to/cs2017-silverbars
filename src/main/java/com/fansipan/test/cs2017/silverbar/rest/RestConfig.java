package com.fansipan.test.cs2017.silverbar.rest;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.web.filter.RequestContextFilter;


public class RestConfig extends ResourceConfig {

    public RestConfig() {
        register(OrderRest.class);
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
        register(RequestContextFilter.class);
        register(JacksonFeature.class);
    }

}
