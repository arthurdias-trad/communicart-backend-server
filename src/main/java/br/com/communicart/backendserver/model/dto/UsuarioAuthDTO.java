package br.com.communicart.backendserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UsuarioAuthDTO {
	
	private Long id;
	private String email;
	private String nome;
}
