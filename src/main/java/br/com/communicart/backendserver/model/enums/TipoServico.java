package br.com.communicart.backendserver.model.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum TipoServico {
	
	FOTOGRAFIA(1, "Fotografia"),
	DESIGN(2, "Design"),
	EDICAO(3, "Edição de imagens"),
	REDACAO(4, "Redação"),
	ILUSTRACAO(5, "Ilustração");
	
	private int cod;
	private String tipo;
	
	private TipoServico(int cod, String tipo) {
		this.cod = cod;
		this.tipo = tipo;
	}
	
	public static TipoServico toEnum(int cod) {
		return Arrays
				.stream(TipoServico.values())
				.filter(x -> cod == x.getCod())
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Código inválido: " + cod));
	}
}
