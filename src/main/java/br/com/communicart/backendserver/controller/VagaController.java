package br.com.communicart.backendserver.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.communicart.backendserver.model.dto.CreateVagaDto;
import br.com.communicart.backendserver.model.dto.VagaResponseDto;
import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.service.VagaService;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
	@Autowired
	private VagaService vagaService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)

	public ResponseEntity<Void> create(@Valid @RequestBody CreateVagaDto vagaDto, @RequestHeader (name="Authorization") String token) {
		Vaga vaga = vagaService.toModel(vagaDto, token);
		vaga = vagaService.create(vaga);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(vaga.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<List<VagaResponseDto>> listar() {
		List<Vaga> vagas = vagaService.listar();
		
		List<VagaResponseDto> vagasDto = vagas.stream()
				.map(vaga -> this.vagaService.toVagaResponseDto(vaga))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(vagasDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VagaResponseDto> findVagaById (@PathVariable Long id) {
		Vaga vaga = vagaService.findVagaById(id);
		VagaResponseDto vagaDto = this.vagaService.toVagaResponseDto (vaga);
		return ResponseEntity.ok().body(vagaDto);
	}
	
	@GetMapping("/usuarios/{perfilId}")
	public ResponseEntity<List<VagaResponseDto>> findVagasByPerfilId(@PathVariable Long perfilId) {
		List<Vaga> vagas = this.vagaService.findVagasByPerfilId(perfilId);
		
		List<VagaResponseDto> vagasDto = vagas.stream()
				.map(vaga -> this.vagaService.toVagaResponseDto(vaga))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(vagasDto);
	}

}
