package br.com.communicart.backendserver.model.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum TipoPessoa {

	PESSOA_FISICA(0, "PF"),
	PESSOA_JURIDICA(1, "PJ");
	
	private int cod;
	private String tipo;
	
	private TipoPessoa(int cod, String tipo) {
		this.cod = cod;
		this.tipo = tipo;
	}

	public static TipoPessoa toEnum(int cod) {
		
		return Arrays
				.stream(TipoPessoa.values())
				.filter(x -> cod == x.getCod())
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Código inválido: " + cod));
	}
}
	