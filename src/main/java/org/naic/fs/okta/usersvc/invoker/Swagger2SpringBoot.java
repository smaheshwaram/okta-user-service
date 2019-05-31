/*
 * Copyright 2019 National Association of Insurance Commissioners
 */
package org.naic.fs.okta.usersvc.invoker;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main service application start up class.
 *
 * @author NAIC
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan( basePackages = {"org.naic.fs.okta.usersvc"} )
public class Swagger2SpringBoot implements CommandLineRunner {
    /**
     * Start up function.
     *
     * @param args Command line arguments.
     */
    public static void main( final String[] args ) {
        new SpringApplication( Swagger2SpringBoot.class ).run( args );
    }

    @Override
    public void run( final String... args ) throws Exception {
        if ( args.length > 0 && "exitcode".equalsIgnoreCase( args[0] ) ) {
//            throw new ExitException();
        }
    }
}
