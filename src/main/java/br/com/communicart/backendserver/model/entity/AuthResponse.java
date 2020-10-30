package br.com.communicart.backendserver.model.entity;

import br.com.communicart.backendserver.model.dto.UsuarioAuthDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AuthResponse {

	private String jwt;
	private UsuarioAuthDTO user;
}
