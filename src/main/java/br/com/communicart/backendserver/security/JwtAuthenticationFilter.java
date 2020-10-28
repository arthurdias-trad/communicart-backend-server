package br.com.communicart.backendserver.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.communicart.backendserver.model.entity.Usuario;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	private final JwtUtil jwtUtil;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			
			chain.doFilter(request, response);
		}
		
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
		
		if (!jwtUtil.validateToken(jwt)) {
			return null;
		}
		
		Usuario usuario = jwtUtil.getUser(jwt);
		return new UsernamePasswordAuthenticationToken(usuario, null);
	}
}
