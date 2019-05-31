/*
 * Copyright 2019 National Association of Insurance Commissioners
 */
package org.naic.fs.okta.usersvc.svc.impl;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.sdk.resource.user.UserList;
//import com.okta.sdk.resource.user.User;

import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.validator.routines.RegexValidator;
import org.naic.fs.okta.usersvc.model.User;
import org.naic.fs.okta.usersvc.model.UserDto;
import org.naic.fs.okta.usersvc.svc.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger LOG = LoggerFactory.getLogger( UsersServiceImpl.class );
    private static final RegexValidator validator = new RegexValidator( "^[a-zA-Z0-9\\._\\-@]{1,200}$" );

    private static void appendSearch( final StringBuilder search, final String attribute, final String value ) {
        if ( StringUtils.isNotBlank( value ) ) {
            if ( !validator.isValid( value ) ) {
                throw new IllegalArgumentException( "Search value invalid." );
            }

            if ( StringUtils.isNotBlank( search ) ) {
                search.append( " and " );
            }

            search.append( attribute );
            search.append( " sw \"" );
            search.append( value.trim() );
            search.append( '"' );
        }
    }

    @Value( "${okta.token.credentials}" )
    private String oktaTokenCreds;

    @Value( "${okta.org.url}" )
    private String oktaUrl;

    private Client client;

    @Override
    public UserDto createUser(final User user) {
//        user = UserBuilder.instance()
//            .setEmail("joe.coder@example.com")
//            .setFirstName("Joe")
//            .setLastName("Code")
//            .buildAndCreate(client);
        return new UserDto();
    }

    @Override
    @PreAuthorize( "isAuthenticated()" )
    public UserDto fetchUsers( final String firstName, final String lastName, final String email, final String login ) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOG.info( "Logged in user is '{}'.", ToStringBuilder.reflectionToString( authentication ) );
        if ( authentication instanceof JwtAuthenticationToken ) {
            final JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;
            for ( final Entry<String, Object> detail : authToken.getTokenAttributes().entrySet() ) {
                LOG.info( "\tTokenDetail {}", detail );
            }
        }
        LOG.info( "Logged in user details is '{}'.",
            ToStringBuilder.reflectionToString( authentication.getDetails() ) );

        if ( StringUtils.isAllBlank( firstName, lastName, email, login ) ) {
            throw new IllegalArgumentException( "Must define at least one search criteria." );
        }

        // See https://developer.okta.com/docs/api/resources/users/#list-users-with-search for details.
        final StringBuilder search = new StringBuilder();
        appendSearch( search, "profile.firstName", firstName );
        appendSearch( search, "profile.lastName", lastName );
        appendSearch( search, "profile.email", email );
        appendSearch( search, "profile.login", login );

        LOG.debug( "Sending search with '{}'.", search );
        final UserList list = fetchClient().listUsers( null, null, null, search.toString(), null );
        LOG.debug( "Got results '{}'.", list );
        final UserDto rVal = new UserDto().total( 0 );
        if ( list != null ) {
            rVal.setData( list.stream()
                .map( item -> item == null || item.getProfile() == null ? null
                    : new User().email( item.getProfile().getEmail() ).firstName( item.getProfile().getFirstName() )
                        .lastName( item.getProfile().getLastName() ).loginName( item.getProfile().getLogin() ) )
                .collect( Collectors.toList() ) );
            rVal.setTotal( rVal.getData().size() ); // TODO - this is wrong, but moves us along for now. Need to
                                                    // understand Okta paging.
        }

        return rVal;
    }

    private Client fetchClient() {
        if ( client == null ) {
            synchronized ( UsersServiceImpl.class ) {
                if ( client == null ) {
                    client = Clients.builder().setClientCredentials( new TokenClientCredentials( oktaTokenCreds ) )
                        .setOrgUrl( oktaUrl ).build();
                }
            }
        }

        return client;
    }
}
