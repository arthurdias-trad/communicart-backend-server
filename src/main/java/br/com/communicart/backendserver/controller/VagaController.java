package br.com.communicart.backendserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	public Vaga create(@Valid @RequestBody CreateVagaDto vagaDto, @RequestHeader (name="Authorization") String token) {
		Vaga vaga = vagaService.toModel(vagaDto, token);
		return vagaService.create(vaga);
	}
	
	@GetMapping
	public List<VagaResponseDto> listar() {
		List<Vaga> vagas = vagaService.listar();
		return vagas.stream().map(vaga -> toVagaResponseDto(vaga)).collect(Collectors.toList());
	}
	
	private VagaResponseDto toVagaResponseDto(Vaga vaga) {
		return VagaResponseDto.builder()
			.id(vaga.getId())
			.perfilId(vaga.getPerfil().getId())
			.titleJob(vaga.getTitleJob())
			.typeJob(vaga.getTypeJob().getTipo())
			.description(vaga.getDescription())
			.price(vaga.getPrice())
			.paymentType(vaga.getPaymentType())
			.paymentToNegotiate(vaga.getPaymentToNegotiate())
			.contactForms(vaga.getContactForms())
			.statusVaga(vaga.getStatusVaga())
			.build();
	}

}
