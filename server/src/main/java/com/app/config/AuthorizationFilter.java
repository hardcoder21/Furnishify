package com.app.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Extract roles from authentication and perform role-based authorization
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean hasAdminRole = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            boolean hasUserRole = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));
            
            System.out.println("Admin "+hasAdminRole);
            System.out.println("User "+hasUserRole);
            System.out.println(authorities);

            if (!hasAdminRole && !hasUserRole) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
        }
        filterChain.doFilter(request, response);

	}

}
