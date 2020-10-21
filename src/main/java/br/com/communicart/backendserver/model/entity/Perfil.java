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
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "perfis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@OneToOne(mappedBy = "perfil")
	private Usuario usuario;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "pessoa_fisica_id", nullable = true)
	private PessoaFisica PF;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "pessoa_juridica_id", nullable = true)
	private PessoaJuridica PJ;
	
	@Column(nullable = true, length=1000)
	@Size(max=1000)
	private String bio;
}
