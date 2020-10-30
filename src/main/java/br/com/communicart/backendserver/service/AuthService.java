package br.com.communicart.backendserver.service;

import javax.validation.Valid;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.model.entity.AuthResponse;
import br.com.communicart.backendserver.model.entity.Usuario;
import br.com.communicart.backendserver.security.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final UsuarioService usuarioService;
	private final JwtUtil jwtUtil;
	
	public AuthResponse authenticateUser(@Valid Credential credential) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						credential.getUsername(),
						credential.getPassword()
				)
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Usuario usuario = this.usuarioService.findByEmail(credential.getUsername());
		String jwt = jwtUtil.generateToken(usuario);
		
		return AuthResponse.builder()
				.jwt(jwt)
				.build();
	}
	
}
