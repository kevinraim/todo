package com.todo.todo.common.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

  @Value("${security.jwt.secret}")
  private String SECRET;

  @Value("${security.jwt.timeMilis}")
  private int EXPIRATION;

  @Value("${security.jwt.issuer}")
  private String ISSUER;

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractIssuer(String token) {
    return extractClaim(token, Claims::getIssuer);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer(ISSUER)
        .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(SignatureAlgorithm.HS256, SECRET).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = this.extractEmail(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }
}
