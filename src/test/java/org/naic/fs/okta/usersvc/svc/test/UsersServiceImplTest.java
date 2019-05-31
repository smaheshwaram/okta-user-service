/*
 * Copyright 2019 National Association of Insurance Commissioners
 */
package org.naic.fs.okta.usersvc.svc.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.naic.fs.okta.usersvc.invoker.Swagger2SpringBoot;
import org.naic.fs.okta.usersvc.model.UserDto;
import org.naic.fs.okta.usersvc.svc.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * jUnit test
 *
 * @author NAIC
 */
@RunWith( SpringRunner.class )
@SpringBootTest( classes = Swagger2SpringBoot.class )
public class UsersServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger( UsersServiceImplTest.class );

    private static void validateResult( final UserDto rVal ) {
        LOG.info( "Got Result '{}'.", rVal );

        Assert.assertNotNull( rVal );
        Assert.assertTrue( 0 < rVal.getTotal() );
        Assert.assertNotNull( rVal.getData() );
        Assert.assertFalse( rVal.getData().isEmpty() );
    }

    @Autowired( required = true )
    private UsersService svc;

    /**
     * Test method for
     * {@link org.naic.fs.okta.usersvc.svc.impl.UsersServiceImpl#fetchUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
//    @Test
//    @WithMockUser( username = "msheehan", roles = {"USER"} )
//    public void testFetchUsers() {
////        validateResult( svc.fetchUsers( "m", "s", "m", "m" ) );
//        validateResult( svc.fetchUsers( "m", "s", "msheehan@naic.org", null ) );
//        validateResult( svc.fetchUsers( "m", "s", null, null ) );
//        validateResult( svc.fetchUsers( "m", null, null, null ) );
//    }

    /**
     * Test method for
     * {@link org.naic.fs.okta.usersvc.svc.impl.UsersServiceImpl#fetchUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test( expected = IllegalArgumentException.class )
    @WithMockUser( username = "msheehan", roles = {"USER"} )
    public void testFetchUsersError() {
        svc.fetchUsers( null, null, null, null );
    }

    /**
     * Test method for
     * {@link org.naic.fs.okta.usersvc.svc.impl.UsersServiceImpl#fetchUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test( expected = IllegalArgumentException.class )
    @WithMockUser( username = "msheehan", roles = {"USER"} )
    public void testFetchUsersErrorBadArg() {
        svc.fetchUsers( "m\"", null, null, null );
    }
}
