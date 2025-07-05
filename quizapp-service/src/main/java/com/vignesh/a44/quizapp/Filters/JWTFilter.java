package com.vignesh.a44.quizapp.Filters;

import com.vignesh.a44.quizapp.Service.CustomUserDetailsService;
import com.vignesh.a44.quizapp.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(JWTFilter.class);

    @Autowired
    ApplicationContext context;

    @Autowired
    JWTService jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JWTFilter triggered!");

        String requestUri = request.getRequestURI();

        //Check and ignore if the request url is for login/signup
        if (requestUri.equalsIgnoreCase("/hello") || requestUri.equalsIgnoreCase("/login") || requestUri.equalsIgnoreCase("/signup")) {
            log.info("Ignoring JWT Extraction process since the route is: {}", requestUri);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userId = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.info("Authorization content found in the header!");
            token = authHeader.substring(7);
            userId = jwt.extractUserEmail(token);
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            log.info("UserId found!");
            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(userId);
            if (jwt.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }

}
