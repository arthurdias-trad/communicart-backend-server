package br.com.communicart.backendserver.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.model.dto.UsuarioDTO;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Usuario;
import br.com.communicart.backendserver.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public Usuario create(UsuarioDTO usuarioDto) {
		Usuario usuario = this.fromDTO(usuarioDto);
		
		return this.usuarioRepository.save(usuario);
	}
	
	public Usuario findByEmail(String email) {
		Usuario usuario = this.usuarioRepository.findByEmail(email)
					.orElseThrow(() -> new ObjectNotFoundException("Usuário ou senha incorreto"));
	
		
		return usuario;
	}
	
	public Usuario fromDTO(UsuarioDTO usuarioDto) {
		Usuario usuario = Usuario.builder()
				.email(usuarioDto.getEmail())
				.password(passwordEncoder.encode(usuarioDto.getPassword()))
				.perfil(Perfil.builder().build())
				.build();
		
		return usuario;
	}
}
