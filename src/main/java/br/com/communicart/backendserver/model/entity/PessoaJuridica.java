package br.com.communicart.backendserver.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoas_juridicas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaJuridica extends Pessoa {

	@Column(length = 100, nullable = false, name = "nome_fantasia")
	@NotNull @NotEmpty @Size(min = 3, max = 100)
	private String nomeFantasia;
	
	@Column(length = 100, nullable = false, name = "nome_responsavel")
	@NotNull @NotEmpty @Size(min = 3, max = 100)
	private String nomeRepresentante;
	
	@Column(length=14, nullable = false, unique = true)
	@CNPJ
	private String cnpj;
	
}
