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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@OneToOne(mappedBy = "perfil")
	@JsonIgnore
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
	
	@Column(nullable = true, length=120)
	@Size(max=120)
	@URL
	private String website;
	
	@Column(nullable = true, length=600)
	@Size(max=600)
	private String interesses;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "midias_sociais_id", nullable = true)
	private MidiasSociais midiasSociais;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "servicos_id", nullable = true)
	private Servicos servicos;
	
	public boolean hasPessoa() {
		return this.PF != null || this.PJ != null;
	}
}
