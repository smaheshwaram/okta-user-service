/*
 * Copyright 2018 National Association of Insurance Commissioners
 */
package org.naic.fs.okta.usersvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configure the web environment.
 *
 * @author NAIC
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String[] corsAllowedMethods = new String[]{"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};
    private static final int MAX_AGE_SECS = 300;

    @Value( "${optins.cors.domains}" )
    private String[] domains;

    @Override
    public void addCorsMappings( final CorsRegistry registry ) {
        registry.addMapping( "/**" ).allowedOrigins( domains ).maxAge( MAX_AGE_SECS )
            .allowedMethods( corsAllowedMethods ).allowedHeaders( "*" );
    }

    @Override
    public void addResourceHandlers( final ResourceHandlerRegistry registry ) {
        registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
        registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
    }

    @Override
    public void addViewControllers( final ViewControllerRegistry registry ) {
        registry.addRedirectViewController( "/", "swagger-ui.html" );
    }

    @Override
    public void extendMessageConverters( final List<HttpMessageConverter<?>> converters ) {
        for ( final HttpMessageConverter<?> converter : converters ) {
            if ( converter instanceof MappingJackson2HttpMessageConverter ) {
                final MappingJackson2HttpMessageConverter jsonMessageConverter
                    = (MappingJackson2HttpMessageConverter) converter;
                final ObjectMapper objectMapper = jsonMessageConverter.getObjectMapper();
                objectMapper.disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS );
                break;
            }
        }
    }
}
