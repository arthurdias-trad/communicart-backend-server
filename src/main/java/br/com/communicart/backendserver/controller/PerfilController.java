package br.com.communicart.backendserver.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.communicart.backendserver.model.dto.CreatePessoaDTO;
import br.com.communicart.backendserver.model.dto.UpdatePerfilDTO;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.enums.TipoPessoa;
import br.com.communicart.backendserver.service.PerfilService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@AllArgsConstructor
public class PerfilController {
	
	private final PerfilService perfilService;
	
	@GetMapping("/{idPerfil}")
	public ResponseEntity<Perfil> findById(@PathVariable Long idPerfil) {
		Perfil perfil = this.perfilService.findById(idPerfil);
		
		return ResponseEntity.ok(perfil);
	}
	
	@PatchMapping("/{idPerfil}")
	public ResponseEntity<Void> register(@PathVariable Long idPerfil, @RequestBody CreatePessoaDTO pessoaDto) {
		
		if (pessoaDto.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)) {
			this.perfilService.createPessoaFísica(pessoaDto, idPerfil);
		} else if (pessoaDto.getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA)) {
			this.perfilService.createPessoaJuridica(pessoaDto, idPerfil);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{idPerfil}")
	public ResponseEntity<Void> update(@PathVariable Long idPerfil, @Valid @RequestBody UpdatePerfilDTO perfilDto) {
		this.perfilService.update(idPerfil, perfilDto);
		
		return ResponseEntity.noContent().build();
	} 
	
}
