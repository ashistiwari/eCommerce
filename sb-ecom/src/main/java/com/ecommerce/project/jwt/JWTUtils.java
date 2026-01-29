package com.ecommerce.project.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTUtils {
    private static final Logger logger= LoggerFactory.getLogger(JWTUtils.class);
    //Generate JWT from Header
    public String generateToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        logger.debug("Bearer token is {}",bearerToken);
        if (bearerToken!=null && bearerToken.startsWith("% Bearer %")){//remove bearer prefix
            return bearerToken.substring(7);
        }
        return null;
    }
    //Generating token from username
    public String generateTokenFromUsername(UserDetails userDetails) {

    }
    //Getting username from jwt token
    //Generate Signing key
    // Validate JWt token

}
