package br.com.communicart.backendserver.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.communicart.backendserver.model.dto.UsuarioDTO;
import br.com.communicart.backendserver.model.entity.Usuario;
import br.com.communicart.backendserver.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody UsuarioDTO usuarioDto) {
		Usuario usuario = this.usuarioService.create(usuarioDto);
		
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(usuario.getId())
					.toUri();
			
		return ResponseEntity.created(uri).build();
	}
	

}
