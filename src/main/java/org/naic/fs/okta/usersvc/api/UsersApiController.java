package org.naic.fs.okta.usersvc.api;

import io.swagger.annotations.ApiParam;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.naic.fs.okta.usersvc.model.User;
import org.naic.fs.okta.usersvc.model.UserDto;
import org.naic.fs.okta.usersvc.svc.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

@Controller
@RequestMapping( "${openapi.nAICOktaPOCService.base-path:}" )
public class UsersApiController implements UsersApi {
    private static final Logger LOG = LoggerFactory.getLogger( UsersApiController.class );

    @Autowired( required = true )
    UsersService svc;

    private final NativeWebRequest request;

    @Autowired( required = true )
    public UsersApiController( final NativeWebRequest request ) {
        super();
        LOG.trace( "UsersApiController()" );
        this.request = request;
    }

    @Override
    public ResponseEntity<UserDto> fetchUsers(
        @Pattern( regexp = "[a-zA-Z0-9\\._\\-@]{1,200}" ) @Size( min = 1,
            max = 200 ) @ApiParam( value = "Search by first name" ) @Valid @RequestParam( value = "firstName",
                required = false ) final String firstName,
        @Pattern( regexp = "[a-zA-Z0-9\\._\\-@]{1,200}" ) @Size( min = 1,
            max = 200 ) @ApiParam( value = "Search by last name" ) @Valid @RequestParam( value = "lastName",
                required = false ) final String lastName,
        @Pattern( regexp = "[a-zA-Z0-9\\._\\-@]{1,200}" ) @Size( min = 1,
            max = 200 ) @ApiParam( value = "Search by email name" ) @Valid @RequestParam( value = "email",
                required = false ) final String email,
        @Pattern( regexp = "[a-zA-Z0-9\\._\\-@]{1,200}" ) @Size( min = 1,
            max = 200 ) @ApiParam( value = "Search by login name" ) @Valid @RequestParam( value = "login",
                required = false ) final String login ) {
        LOG.trace( "fetchUsers()" );
        try {
            return new ResponseEntity<>( svc.fetchUsers( firstName, lastName, email, login ), HttpStatus.OK );
        }
        catch ( final Throwable e ) {
            LOG.error( String.valueOf( e ), e );
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    @Override
    public ResponseEntity<UserDto> createUser(@ApiParam(value = "Creating a user" ,required=true )
    @Valid @RequestBody final User user) {
        LOG.trace( "createUser()" );
        try {
            return new ResponseEntity<>( svc.createUser( user ), HttpStatus.OK );
        }
        catch ( final Throwable e ) {
            LOG.error( String.valueOf( e ), e );
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable( request );
    }
}
