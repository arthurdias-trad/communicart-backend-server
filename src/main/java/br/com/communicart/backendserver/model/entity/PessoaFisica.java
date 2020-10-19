package br.com.communicart.backendserver.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoas_fisicas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaFisica extends Pessoa {

	@Column(length = 50, nullable = false)
	@NotNull @NotEmpty @Size(min = 3, max = 50)
	private String nome;
	
	@Column(length = 100, nullable = false)
	@NotNull @NotEmpty @Size(min = 3, max = 100)
	private String sobrenome;
	
	@Column(length=11, nullable = false, unique = true)
	@CPF
	private String cpf;
	
}
