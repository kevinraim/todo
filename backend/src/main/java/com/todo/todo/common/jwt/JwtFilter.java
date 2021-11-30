package com.todo.todo.common.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.todo.todo.service.UserServiceImpl;


@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private UserServiceImpl userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Value("${security.jwt.bearer}")
  private String BEARER;

  @Value("${security.jwt.issuer}")
  private String ISSUER;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
      jwt = authorizationHeader.substring(BEARER.length());
      username = jwtUtil.extractEmail(jwt);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
          && ISSUER.equals(jwtUtil.extractIssuer(jwt))) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (jwtUtil.validateToken(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken usernamePassAuthToken =
              new UsernamePasswordAuthenticationToken(userDetails, null,
                  userDetails.getAuthorities());

          usernamePassAuthToken
              .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(usernamePassAuthToken);
        }
      }

    }
    filterChain.doFilter(request, response);

  }
}

