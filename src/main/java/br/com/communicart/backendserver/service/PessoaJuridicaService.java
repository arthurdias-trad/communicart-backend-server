package br.com.communicart.backendserver.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.model.entity.PessoaJuridica;
import br.com.communicart.backendserver.repository.PessoaJuridicaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PessoaJuridicaService {
	
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	
	public PessoaJuridica create(@Valid PessoaJuridica pessoaJuridica) {
		return this.pessoaJuridicaRepository.save(pessoaJuridica);
	}
}
