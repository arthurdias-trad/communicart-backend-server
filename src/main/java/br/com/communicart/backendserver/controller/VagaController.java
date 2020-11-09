package br.com.communicart.backendserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.service.VagaService;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
	@Autowired
	private VagaService vagaService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Vaga create(@Valid @RequestBody Vaga vaga) {
		return vagaService.create(vaga);
	}

}
