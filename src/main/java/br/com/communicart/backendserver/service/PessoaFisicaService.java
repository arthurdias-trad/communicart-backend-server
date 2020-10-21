package br.com.communicart.backendserver.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.model.entity.PessoaFisica;
import br.com.communicart.backendserver.repository.PessoaFisicaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PessoaFisicaService {
	
	private final PessoaFisicaRepository pessoaFisicaRepository;
	
	public PessoaFisica create(@Valid PessoaFisica pessoaFisica) {
		return this.pessoaFisicaRepository.save(pessoaFisica);
	}
}
