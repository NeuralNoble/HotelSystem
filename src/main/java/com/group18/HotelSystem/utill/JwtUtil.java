package com.group18.HotelSystem.utill;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtUtil {
    private String generateToken(Map<String,Object> extraClaims , UserDetails details){
        return builder().setClaims(extraClaims).setSubject(details.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();

    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode("dXNlciBpcyB2YWxpZA==");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
