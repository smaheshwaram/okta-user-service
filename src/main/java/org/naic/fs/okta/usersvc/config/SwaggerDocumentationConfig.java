/*
 * Copyright 2018 National Association of Insurance Commissioners
 */
package org.naic.fs.okta.usersvc.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configures the documentation bean for the service.
 *
 * @author NAIC
 */
@javax.annotation.Generated( value = "io.swagger.codegen.languages.SpringCodegen",
    date = "2018-08-29T14:42:47.643-05:00" )
@Configuration
public class SwaggerDocumentationConfig {
    public static final class BasePathAwareRelativePathProvider extends RelativePathProvider {
        private final String basePath;

        public BasePathAwareRelativePathProvider( final ServletContext servletContext, final String basePath ) {
            super( servletContext );
            this.basePath = basePath;
        }

        @Override
        public String getOperationPath( final String operationPath ) {
            final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath( "/" );
            return Paths.removeAdjacentForwardSlashes(
                uriComponentsBuilder.path( operationPath.replaceFirst( "^" + basePath, "" ) ).build().toString() );
        }

        @Override
        protected String applicationPath() {
            return Paths.removeAdjacentForwardSlashes(
                UriComponentsBuilder.fromPath( super.applicationPath() ).path( basePath ).build().toString() );
        }
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder().title( "NAIC Okta POC Service API" )
            .description( "NAIC Okta POC application service API" ).license( "NAIC General Internal Use License" )
            .version( "1.0.0" ).contact( new Contact( "NAIC", "https://www.naic.org", "OPTinsStaff@naic.org" ) )
            .build();
    }

    /**
     * Build docket bean.
     *
     * @return Docket bean.
     */
    @Bean
    public Docket customImplementation( final ServletContext servletContext,
        @Value( "${openapi.nAICOktaPOCService.base-path:}" ) final String basePath ) {
        return new Docket( DocumentationType.SWAGGER_2 ).select()
            .apis( RequestHandlerSelectors.basePackage( "org.naic.fs.okta.usersvc.api" ) ).build()
            .pathProvider( new BasePathAwareRelativePathProvider( servletContext, basePath ) )
            .directModelSubstitute( java.time.LocalDate.class, java.sql.Date.class )
            .directModelSubstitute( java.time.OffsetDateTime.class, java.util.Date.class ).apiInfo( apiInfo() );
    }
}
