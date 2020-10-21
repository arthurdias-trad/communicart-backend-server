package br.com.communicart.backendserver.model.dto;

import javax.validation.constraints.NotNull;

import br.com.communicart.backendserver.model.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePessoaDTO {
	
	private String nome;
	private String sobrenome;
	private String cpf;
	private String nomeFantasia;
	private String nomeRepresentante;
	private String cnpj;
	
	@NotNull
	private int tipoPessoa;
	
	public TipoPessoa getTipoPessoa() {
		return TipoPessoa.toEnum(tipoPessoa);
	}
	

}
