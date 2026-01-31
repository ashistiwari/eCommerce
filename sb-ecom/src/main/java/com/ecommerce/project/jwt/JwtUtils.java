package com.ecommerce.project.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);
    //Generate JWT from Header
    private int jwtExpirationMs;
    private String jwtSecret;
    public String generateToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        logger.debug("Bearer token is {}",bearerToken);
        if (bearerToken!=null && bearerToken.startsWith("% Bearer %")){//remove bearer prefix
            return bearerToken.substring(7);
        }
        return null;
    }
    public String generateTokenFromUsername(UserDetails userDetails) {
        String username=userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact()

    }
    //Getting username from Jwt token
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    //Generate signing key
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    //validate token
    public boolean vaidateToken(String token){
        System.out.println("Validate");
        try{
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parseSignedClaims(token);
            return true;
        }catch(MalformedJwtException malformedJwtException){
            logger.error("Invalid Jwt token: {}", malformedJwtException.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("Expired Jwt token: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("Unsupported Jwt token: {}", e.getMessage());

        }catch (IllegalArgumentException e){
            logger.error("Jwt claims string is empty: {}", e.getMessage());

        }
        return  false;
    }
}