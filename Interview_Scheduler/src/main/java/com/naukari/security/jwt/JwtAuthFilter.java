package com.naukari.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.naukari.models.User;
import com.naukari.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		try {
//			String jwt = jwtUtils.getJwtFromCookies(request);
//			if(jwt != null) {
//				if(jwtUtils.validateJwtToken(jwt)) {
//					String username = jwtUtils.getUsernameFromToken(jwt);
//					User user = (User) userDetailsServiceImpl.loadUserByUsername(username);
//					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//				}
//				else {
//					throw new IOException("Invalid credentials.");
//				}
//			}
//			else {
//				throw new IOException("Invalid token.");
//			}
//		} catch (Exception e) {
//			logger.error("Cannot set user authentiction : "+e);
//		}
//		filterChain.doFilter(request, response);
//	}

	// Another way to filter
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = jwtUtils.getJwtFromCookies(request);
			if (jwt != null) {
				String username = jwtUtils.getUsernameFromToken(jwt);
				if (username != null) {
					User user = (User) userDetailsServiceImpl.loadUserByUsername(username);
					if (user != null && jwtUtils.validateJwtToken(jwt, user)) {
						UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
						authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					} else {
						throw new IOException("Invalid user.");
					}
				} else {
					throw new IOException("User not found with username : " + username);
				}
			} else {
				throw new IOException("Invalid jwt token");
			}
		} catch (Exception e) {
			logger.error("Cannot set authentication : {} " + e);
		}
		filterChain.doFilter(request, response);
	}
}
