package br.com.communicart.backendserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.communicart.backendserver.model.dto.CreatePessoaDTO;
import br.com.communicart.backendserver.model.enums.TipoPessoa;
import br.com.communicart.backendserver.service.PerfilService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@AllArgsConstructor
public class PerfilController {
	
	private final PerfilService perfilService;
	
	@PatchMapping("/{idPerfil}")
	public ResponseEntity<Void> register(@PathVariable Long idPerfil, @RequestBody CreatePessoaDTO pessoaDto) {
		
		if (pessoaDto.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)) {
			this.perfilService.createPessoaFísica(pessoaDto, idPerfil);
		} else if (pessoaDto.getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA)) {
			this.perfilService.createPessoaJuridica(pessoaDto, idPerfil);
		}
		
		return ResponseEntity.noContent().build();
	}
}
