/*
 * Copyright 2019 National Association of Insurance Commissioners
 */
package org.naic.fs.okta.usersvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web service security configuration.
 *
 * @author NAIC
 */
@Configuration
@EnableWebSecurity( debug = true )
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure( final HttpSecurity http ) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().oauth2ResourceServer().jwt();
        http.cors().and().csrf().disable();
    }
}
