package com.Bhuvaneswar.MediumBloggerApplication.Security;

import com.Bhuvaneswar.MediumBloggerApplication.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class JWTAuthenticationManager implements AuthenticationManager
{
    private JWTService jwtService;
    private UserService userService;

    public JWTAuthenticationManager(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        if(authentication instanceof JWTAuthenticator)
        {
            var jwtAuthentication=(JWTAuthenticator) authentication;
            var jwt=jwtAuthentication.getCredentials();
            var userId=jwtService.retriveUserId(jwt);
            var userEntity=userService.getUser(userId);

            jwtAuthentication.userEntity=userEntity;
            jwtAuthentication.setAuthenticated(true);
//            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            return jwtAuthentication;
        }
        throw new IllegalArgumentException("cannot authenticate non-JWT authentication");
    }
}
