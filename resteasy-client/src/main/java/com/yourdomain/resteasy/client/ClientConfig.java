package com.yourdomain.resteasy.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Value("${api.v1.url}")
    private String url;

    @Bean
    public UserResourceV1 getUserResourceV1(){

        ResteasyClient client = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = client.target(url);
        UserResourceV1 proxy = target.proxy(UserResourceV1.class);
        return proxy;

    }

//    @Bean
//    public FacebookInterface getFacebookResourse(){
//
//        ResteasyClient client = new ResteasyClientBuilder().build();
//
//        ResteasyWebTarget target = client.target(urlFacebook);
//        FacebookInterface proxy = target.proxy(FacebookInterface.class);
//        return proxy;
//
//    }
}
