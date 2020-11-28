package br.com.communicart.backendserver.model.dto;

import java.net.URL;

import br.com.communicart.backendserver.model.entity.MidiasSociais;
import br.com.communicart.backendserver.model.entity.Servicos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilResponseDTO {
	private String nome;
	private String nomeRepresentante;
	private String bio;
	private String website;
	private String interesses;
	private URL imageURL;
	private MidiasSociais midiasSociais;
	private Servicos servicos;
	private String type;
}
