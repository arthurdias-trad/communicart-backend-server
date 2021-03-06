package br.com.communicart.backendserver.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
	@Column(nullable = true, name = "image_url")
	private java.net.URL imageURL;
	
	@Column(nullable = true, name = "rates_freelance")
	private String ratesFreelancer;
	
	@Column(nullable = true, name = "rates_contratante")
	private String ratesContratante;
		
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "midias_sociais_id", nullable = true)
	private MidiasSociais midiasSociais;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "servicos_id", nullable = true)
	private Servicos servicos;

	//Vagas que o usuario criou como contratante
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
	@JsonIgnore
	private List<Vaga> vagasCadastradas;
	
	//Vagas que o usuario se candidatou
	@OneToMany(mappedBy = "perfil")
	@JsonIgnore
	private List<VagaCandidatura> candidaturas;
	
	//Trabalhos em que o usuario foi selecionado para realizar
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "selectedFreelancer")
	@JsonIgnore
	private List<Vaga> meuTrabalhos;
	
	public boolean hasPessoa() {
		return this.PF != null || this.PJ != null;
	}	
}
