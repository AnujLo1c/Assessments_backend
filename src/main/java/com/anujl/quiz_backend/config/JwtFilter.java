package com.anujl.quiz_backend.config;

import com.anujl.quiz_backend.entity.UserEntity;
import com.anujl.quiz_backend.repository.UserRepo;
import com.anujl.quiz_backend.service.CustomUserDetailService;
import com.anujl.quiz_backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        Date issuedAt=null;

        String requestPath = request.getServletPath();
//        String requestPath = request.getRequestURI();

        System.out.println("Request Path: " + requestPath);
        if (requestPath.startsWith("/api/auth/") ) {
            System.out.println("Bypassing JWT filter for path: " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Auth Header: " + authHeader+"END");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

                token = token.trim();
            System.out.println("Token:"+token);
            username = jwtService.extractUserName(token);

            issuedAt = jwtService.extractIssuedAt(token);
        }
        System.out.println("Token2:"+token);
        if (issuedAt!=null && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userEntity = userRepo.findByUsername(username)
                    .orElse(null);
            UserDetails userDetails = context.getBean(CustomUserDetailService.class).loadUserByUsername(username);

            if (userEntity != null && userEntity.getLastLoginTime() != null) {
                if (issuedAt.toInstant().isBefore( userEntity.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant())) {
                    System.out.println("1Token issued at: " + issuedAt + " is before last login time: " + userEntity.getLastLoginTime());

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired by logout");
                    return;
                }

                System.out.println("Token issued at: " + issuedAt + " is before last login time: " + userEntity.getLastLoginTime());
                System.out.println("Token issued at: " + issuedAt);
            }
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
