package br.com.cinq.spring.data.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Register Jersey modules
 * @author Adriano Kretschmer
 * 
 * Changed by Andre Brito Fonseca
 * <ul><li>Added package registration</li></ul>
 */
@Configuration
@ApplicationPath("rest")
public class Config extends ResourceConfig {

    public Config() {
        packages("br.com.cinq.spring.data.resource");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }


}