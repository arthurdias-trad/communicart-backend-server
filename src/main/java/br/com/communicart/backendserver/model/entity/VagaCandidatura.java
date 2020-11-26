package br.com.communicart.backendserver.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VagaCandidatura implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "vaga_id")
	@JsonIgnore
	private Vaga vaga;
	
	@ManyToOne
	@JoinColumn(name = "perfil_id")
	@JsonIgnore
	private Perfil perfil;
	
	private LocalDateTime registeredAt;

	@NotNull
	private BigDecimal price;
	
	@NotNull
	private LocalDateTime deliveryDate;
	
	@NotBlank
	private String observations;
}