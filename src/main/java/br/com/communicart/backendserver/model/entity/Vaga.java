package br.com.communicart.backendserver.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.communicart.backendserver.model.enums.PaymentType;
import br.com.communicart.backendserver.model.enums.StatusVaga;
import br.com.communicart.backendserver.model.enums.TipoServico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vagas")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vaga implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@OneToOne()
	@JsonIgnore
	private Perfil perfil;
	
	@NotBlank
	private String titleJob;
	
	@Enumerated(EnumType.STRING)
	private TipoServico typeJob;
	
	@NotNull
	private String description;
	
	@NotNull
	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private PaymentType paymentType;
	
	private Boolean paymentToNegotiate;
	
	@Column(nullable = true, name = "file_url")
	private URL fileURL;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_forms_id")
	private ContactFormsAvailableForJob contactForms;
	
	@Enumerated(EnumType.STRING)
	private StatusVaga statusVaga;
	
	// Representa qual o candidato escolhido para realizar um job
	@OneToOne
	@JsonIgnore
	private Perfil selectedFreelancer;
	
	//Freelancer que se candidataram a uma vaga
	@OneToMany(mappedBy = "vaga")
	private List<VagasCandidaturas> candidaturas;
	
}
