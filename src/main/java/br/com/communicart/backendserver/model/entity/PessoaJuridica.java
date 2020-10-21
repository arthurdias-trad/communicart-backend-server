package br.com.communicart.backendserver.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PessoaJuridica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(length = 100, nullable = false, name = "nome_fantasia")
	@NotNull @NotEmpty @Size(min = 3, max = 100)
	private String nomeFantasia;
	
	@Column(length = 100, nullable = false, name = "nome_responsavel")
	@NotNull @NotEmpty @Size(min = 3, max = 100)
	private String nomeRepresentante;
	
	@Column(length=14, nullable = false, unique = true)
	@CNPJ
	private String cnpj;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "perfil_id")
	@JsonIgnore
	private Perfil perfil;
}
