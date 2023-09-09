package com.binaklet.binaklet.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String JWT_SECRET = "16743d13dcb272fb031d20d8c5c84343fd055864d43e562003234a42b84400da";
    public String extractUsername(String jwt){
        return extractClaim(jwt,Claims::getSubject);
    }

    public String generateToken (UserDetails userDetails){
        System.out.println("Trying to generate token");
        System.out.println(userDetails.toString());


        String generatedToken =  generateToken(new HashMap<>(),userDetails);
        System.out.println(generatedToken);
    return generatedToken;
    }




    public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*36000*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver ){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwt){
        return Jwts.parserBuilder().
                setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignInKey(){
     byte[] keyBytes = Decoders.BASE64.decode(this.JWT_SECRET);
     return Keys.hmacShaKeyFor(keyBytes);
    }

}
