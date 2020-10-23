package br.com.communicart.backendserver.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servicos {
	
	private Long id;
	
	private boolean fotografia;
	private boolean design;
	private boolean edicao;
	private boolean redacao;
}
