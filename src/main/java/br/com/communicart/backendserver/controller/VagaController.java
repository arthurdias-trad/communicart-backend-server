package br.com.communicart.backendserver.controller;

import java.net.URI;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.exception.handler.ResponseError;
import br.com.communicart.backendserver.model.dto.CandidaturaVagaDto;
import br.com.communicart.backendserver.model.dto.CreateVagaDto;
import br.com.communicart.backendserver.model.dto.VagaResponseDto;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.entity.VagaCandidatura;
import br.com.communicart.backendserver.model.enums.StatusVaga;
import br.com.communicart.backendserver.security.JwtUtil;
import br.com.communicart.backendserver.service.PerfilService;
import br.com.communicart.backendserver.service.VagaService;
import br.com.communicart.backendserver.service.VagaCandidaturaService;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
	@Autowired
	private VagaService vagaService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private VagaCandidaturaService vagaCandidaturaService;
	
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
	
	@GetMapping("listByStatus")
	public ResponseEntity<List<VagaResponseDto>> listarByStatus(@RequestParam StatusVaga statusVaga){
		List<Vaga> vagas = vagaService.listarByStatus(statusVaga);
		
		List<VagaResponseDto> vagasDto = vagas.stream()
				.map(vaga -> this.vagaService.toVagaResponseDto(vaga))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(vagasDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VagaResponseDto> findVagaById (@PathVariable Long id) {
		Vaga vaga = vagaService.findVagaById(id);
		VagaResponseDto vagaDto = this.vagaService.toVagaResponseDto (vaga);
		return ResponseEntity.ok().body(vagaDto);
	}
	
	@PostMapping("{id}/candidatarse")
	public ResponseEntity<ResponseError> cadastrarCandidatura(@PathVariable Long id, @RequestBody CandidaturaVagaDto proposta, @RequestHeader (name="Authorization") String token){
		Long perfilId = this.jwtUtil.getProfileId(token.substring(7));		
		
		Perfil perfil = this.perfilService.findById(perfilId);
		Vaga vaga = vagaService.findVagaById(id);
		
		if(vaga.getPerfil().getId() == perfilId) {
			ResponseError responseError = ResponseError.builder().message("O usuário não pode se candidatar a uma vaga criada por ele mesmo.")
			.path("/api/vagas/"+id+"/candidatarse")
			.name("BAD REQUEST")
			.timestamp(java.sql.Timestamp.valueOf(LocalDateTime.now()).getTime())
			.status(400)
			.build();
			
			return ResponseEntity.badRequest().body(responseError);
		}
		
		vagaCandidaturaService.salvarCandidatura(vaga, perfil, proposta);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateStatus(@RequestParam String statusUpdate, @PathVariable Long id, @RequestHeader (name="Authorization") String token){
		Vaga vaga = vagaService.findVagaById(id);
		
		if(statusUpdate.equals("BLOQUEADA")) {
			vaga.setStatusVaga(StatusVaga.BLOQUEADA);			
			vagaService.update(vaga);
			return ResponseEntity.ok().build();
		}else if(statusUpdate.equals("ATIVA")) {
			vaga.setStatusVaga(StatusVaga.ATIVA);
			vagaService.update(vaga);
			return ResponseEntity.ok().build();
		}else {
			System.out.println("status: "+statusUpdate);
			throw new InvalidParameterException(statusUpdate + " não é reconhceido como um status.");
		}			
	}
	
	@GetMapping("/{idVaga}/candidatos")
	public ResponseEntity<List<Perfil>> listarCandidatosPorVaga(@PathVariable Long idVaga){
		List<Perfil> candidatos = vagaCandidaturaService.findAllCandidatosByVagaId(idVaga);
		
		return ResponseEntity.ok(candidatos);
	}

	@GetMapping("/{idVaga}/candidaturas")
	public ResponseEntity<List<VagaCandidatura>> listarPropostasPorVaga(@PathVariable Long idVaga){
		List<VagaCandidatura> candidaturasPorVaga = vagaCandidaturaService.findAllByVagaId(idVaga);
		return ResponseEntity.ok(candidaturasPorVaga);
	}
	
	@GetMapping("{idVaga}/candidaturas/{idPerfil}")
	public ResponseEntity<VagaCandidatura> listarPropostaPorPerfilId(@PathVariable Long idVaga, @PathVariable Long idPerfil){
		List<VagaCandidatura> candidaturasPorVaga = vagaCandidaturaService.findAllByVagaId(idVaga);
		Perfil perfil = perfilService.findById(idPerfil);
		VagaCandidatura vagaCandidatura = candidaturasPorVaga.stream()
				.filter(candidatura -> candidatura.getPerfil().equals(perfil))
				.findAny()
				.orElseThrow(() -> new ObjectNotFoundException("Candidatura relacionada à vaga não encontrada"));
		
		return ResponseEntity.ok(vagaCandidatura);
	}
	
	@GetMapping("/candidaturas/{idCandidatura}")
	public ResponseEntity<VagaCandidatura> listarProposta (@PathVariable Long idCandidatura){
		VagaCandidatura proposta = vagaCandidaturaService.findById(idCandidatura);
		
		return ResponseEntity.ok(proposta);
	}
	
	@GetMapping("/candidaturas/{idCandidatura}/perfilCandidato")
	public ResponseEntity<Perfil> listarPerfilPorProposta(@PathVariable Long idCandidatura){
		VagaCandidatura proposta = vagaCandidaturaService.findById(idCandidatura);
		
		Perfil perfil = proposta.getPerfil();
		
		return ResponseEntity.ok(perfil);
	}
	
	@GetMapping("usuarios/{perfilId}")
	public ResponseEntity<List<VagaResponseDto>> findVagasByPerfilId(@PathVariable Long perfilId) {
		List<Vaga> vagas = this.vagaService.findVagasByPerfilId(perfilId);
		System.out.println(vagas);
		
		List<VagaResponseDto> vagasDto = vagas.stream()
				.map(vaga -> this.vagaService.toVagaResponseDto(vaga))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(vagasDto);
	}
	
	@GetMapping("contratante/{perfilId}")
	public ResponseEntity<List<VagaResponseDto>> findVagasContratanteByPerfilIdAndStatus(@PathVariable Long perfilId, @RequestParam String statusVaga){
		List<Vaga> vagas = this.vagaService.findVagasByPerfilId(perfilId);
		
		List<VagaResponseDto> vagasDto = vagas.stream()
				.filter(vaga-> vaga.getStatusVaga().toString().equals(statusVaga))
				.map(vaga -> this.vagaService.toVagaResponseDto(vaga))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(vagasDto);
	}
	
	@GetMapping("/candidaturas/freelancer")
	public ResponseEntity<List<VagaResponseDto>> findCandidaturasByIdAndStatus(@RequestHeader (name="Authorization") String token, @RequestParam String statusVaga){
		Long perfilId = this.jwtUtil.getProfileId(token.substring(7));
		List<VagaCandidatura> candidaturas = this.vagaCandidaturaService.findByPerfil(perfilId);
		
		List<VagaResponseDto> vagasDto = candidaturas.stream()
				.map(candidatura -> this.vagaService.toVagaResponseDto(candidatura.getVaga()))
				.filter(vaga -> vaga.getStatusVaga().toString().equals(statusVaga))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(vagasDto);
	}
	
	//Rota selecionar candidato a uma vaga. Implementado usando o id da tabela vaga_candidatura
	@PatchMapping("/selecionar_candidato/{vagaCandidaturaId}")
	public ResponseEntity<Void> selecionarCandidato(@PathVariable Long vagaCandidaturaId, @RequestHeader(name = "Authorization") String token){
//		Long perfilId = this.jwtUtil.getProfileId(token.substring(7));		
//		Perfil perfil = this.perfilService.findById(perfilId);
		
		vagaCandidaturaService.selecionarCandidatoAVaga(vagaCandidaturaId);
		
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("{idVagaCandidatura}/concluirVaga")
	@Transactional
	public ResponseEntity<Void> concluirVaga(@PathVariable Long idVagaCandidatura, @RequestParam int rateContratante){
		VagaCandidatura vagaCandidatura = vagaCandidaturaService.findById(idVagaCandidatura);
		
		vagaCandidatura.setRateContratante(rateContratante);
		vagaCandidaturaService.update(vagaCandidatura);
		
		Vaga vaga = vagaService.findVagaById(vagaCandidatura.getVaga().getId());
		vaga.setStatusVaga(StatusVaga.CONCLUIDA);
		vagaService.update(vaga);
		
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("{idVagaCandidatura}/setRateFreela")
	public ResponseEntity<Void> cadastrarRateFreelancer(@PathVariable Long idVagaCandidatura, @RequestParam int rateFreelancer){
		VagaCandidatura vagaCandidatura = vagaCandidaturaService.findById(idVagaCandidatura);
		
		vagaCandidatura.setRateContratante(rateFreelancer);
		vagaCandidaturaService.update(vagaCandidatura);
		
		
		return ResponseEntity.ok().build();
	}

}
