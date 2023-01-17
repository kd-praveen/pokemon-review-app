package com.pokemonreview.api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJWTFromRequest(request);

        if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)){
            String username = tokenGenerator.getUsernameFromJWT(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // UsernamePasswordAuthenticationToken is an implementation of the Authentication interface which specifies that the user wants to authenticate using a username and password
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, 
            null, 
            userDetails.getAuthorities());

            // WebAuthenticationDetailsSource - It has a single responsibility to convert an instance of HttpServletRequest class into an instance of the WebAuthenticationDetails class. You can think of it as a simple converter.

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // The SecurityContextHolder is where Spring Security stores the details of who is authenticated
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }

    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length()); // remove the "Bearer " from the beginning of the token
        }
        return null;
    }
    
}
