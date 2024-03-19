package com.Bhuvaneswar.MediumBloggerApplication.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService
{
    //TODO: Move the key to a separate .properties file(Not in git)
    private static final String JWT_SECRET_KEY="hbibiuguigiubgigt897980oiopu90u89y897tuujbiuguig";
    private Algorithm algorithm=Algorithm.HMAC256(JWT_SECRET_KEY);
    public String createJWT(Long userId)
    {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
//                .withExpiresAt() //TODO: setup and expiry parameter
                .sign(algorithm);
    }

    public Long retriveUserId(String jwtString)
    {
        var decodedJWT=JWT.decode(jwtString);
        var userId=Long.valueOf(decodedJWT.getSubject());
        return userId;
    }

}
