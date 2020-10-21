package br.com.communicart.backendserver.model.entity;

import br.com.communicart.backendserver.model.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public abstract class Pessoa {
	
	private int tipoPessoa;
	
	public TipoPessoa getTipoPessoa() {
		return TipoPessoa.toEnum(tipoPessoa);
	}
}
