package org.naic.fs.okta.usersvc.svc;

import org.naic.fs.okta.usersvc.model.User;
import org.naic.fs.okta.usersvc.model.UserDto;

public interface UsersService {
    UserDto fetchUsers( String firstName, String lastName, String email, String login );
    UserDto createUser( User user);
}
