package com.notepad.filter;

import com.notepad.jwtService.JwtService;
import com.notepad.jwtService.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final List<String> allowedEndPoints = Arrays.asList("/appuser/addNewAppuser",
            "/appuser/login","/article/getAllPublishedArticle");

    @Autowired
    JwtService jwtService;

    @Autowired
    UserInfoService userInfoService;

    String email = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if(allowedEndPoints.contains(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }
        if (authHeader !=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            email = jwtService.extractUserName(token);
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userInfoService.loadUserByUsername(email);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);

    }

    public String getEmail() {
        return email;
    }
}










