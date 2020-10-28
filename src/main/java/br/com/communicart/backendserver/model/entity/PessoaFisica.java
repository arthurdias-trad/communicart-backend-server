package br.com.communicart.backendserver.model.entity;

import java.io.Serializable;

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

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PessoaFisica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(length = 50, nullable = false)
	@NotNull @NotEmpty @Size(min = 3, max = 50)
	private String nome;
	
	@Column(length = 100, nullable = false)
	@NotNull @NotEmpty @Size(min = 3, max = 100)
	private String sobrenome;
	
	@Column(length=11, nullable = false, unique = true)
	@CPF
	private String cpf;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "perfil_id")
	@JsonIgnore
	private Perfil perfil;
}
