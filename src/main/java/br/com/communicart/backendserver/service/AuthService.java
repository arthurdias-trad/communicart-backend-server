package br.com.communicart.backendserver.service;

import javax.validation.Valid;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.model.dto.UsuarioAuthDTO;
import br.com.communicart.backendserver.model.entity.AuthResponse;
import br.com.communicart.backendserver.model.entity.Perfil;
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
		Perfil perfil = usuario.getPerfil();
		
		String nome = null;
		
		String jwt = jwtUtil.generateToken(usuario);
		if (perfil.getPF() != null) {
			nome = perfil.getPF().getNomeCompleto();
		}
		if (perfil.getPJ() != null) {
			nome = perfil.getPJ().getNomeFantasia();
		}
		
		
		
		return AuthResponse.builder()
				.jwt(jwt)
				.user(UsuarioAuthDTO.builder()
						.id(usuario.getId())
						.email(credential.getUsername())
						.nome(nome)
						.build()
						)
				.build();
	}
	
}
