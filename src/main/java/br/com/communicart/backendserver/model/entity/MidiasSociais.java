package br.com.communicart.backendserver.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "midias_sociais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidiasSociais {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String facebook;
	private String twitter;
	private String linkedin;
	private String instagram;
	
	@OneToOne(mappedBy="midias_sociais")
	private Perfil perfil;
}
