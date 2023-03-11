package com.naukari.security.jwt;

import java.util.Date;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.naukari.models.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

	private final String jwtSecret = "Gaurav";

	private final int jwtExpirationMs = 86400000;

	private final String jwtCookie = "masai";

	// Extracting cookie from HttpServletRequest.
	public String getJwtFromCookies(HttpServletRequest httpServletRequest) {
		Cookie cookie = WebUtils.getCookie(httpServletRequest, jwtCookie);
		if (cookie != null) {
			System.out.println("Cookie name is : " + cookie.getName());
			return cookie.getValue();
		} else {
			return null;
		}
	}

	// Extracting username from token.
	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	// Generating token from username
	public String generateTokenFromUsername(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();			
	}

	// Generating response cookie with help of username
	public ResponseCookie generateResponseCookie(UserDetails userDetails) {
		String jwt = generateTokenFromUsername(userDetails.getUsername());
		ResponseCookie responseCookie = ResponseCookie.from(jwtCookie, jwt)
										.path("/api")
										.maxAge(24*60*60)
										.httpOnly(true)
										.build();
		return responseCookie;
	}
	
	// Checking token is valid or not.
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}
		catch(SignatureException signatureException) {
			System.out.println("SignatureException : "+signatureException.getMessage());
		} catch(MalformedJwtException malformedJwtException) {
			System.out.println("MalformedJwtException : "+malformedJwtException.getMessage());
		} catch(ExpiredJwtException expiredJwtException) {
			System.out.println("ExpiredJwtException : "+expiredJwtException.getMessage());
		} catch(UnsupportedJwtException unsupportedJwtException) {
			System.out.println("UnsupportedJwtException : "+unsupportedJwtException.getMessage());
		} catch(IllegalArgumentException illegalArgumentException) {
			System.out.println("IllegalArgumentException : "+illegalArgumentException.getMessage());
		}
		return false;
	}
	
	// Another way to validate token
	public boolean validateJwtToken(String authToken, User user) {
		String username = getUsernameFromToken(authToken);
		return user.getUsername().equals(username);
	}
	
	// This method is used to clear the cookies (for logout).
	public ResponseCookie getCleanCookie() {
		ResponseCookie responseCookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
		return responseCookie;
	}
	
}
