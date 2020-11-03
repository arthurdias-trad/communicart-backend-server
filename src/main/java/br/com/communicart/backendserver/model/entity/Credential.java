package br.com.communicart.backendserver.model.entity;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Credential {

	@NotNull
	public String email;
	
	@NotNull
	public String password;

}
