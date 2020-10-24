package br.com.communicart.backendserver.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import br.com.communicart.backendserver.model.entity.MidiasSociais;
import br.com.communicart.backendserver.model.entity.Servicos;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePerfilDTO {
	
	@Builder
	public UpdatePerfilDTO(String bio, String website, String[] interesses) {
		this.bio = bio;
		this.website = website;
		this.interesses = interesses;
	}

	@Column(nullable = true, length=1000)
	@Size(max=1000)
	private String bio;
	
	@Column(nullable = true, length=120)
	@Size(max=120)
	@URL
	private String website;
	
	private String[] interesses;
	
	private MidiasSociais midiasSociais;
	
	private Servicos servicos;
	
	
	public String getInteresses() {
		return this.interesses.length > 0 ? this.toCSV(this.interesses) : null;
	}
	
	public String toCSV(String[] list) {
		String csv = String.join(",", list);
		return csv;
	}
}
