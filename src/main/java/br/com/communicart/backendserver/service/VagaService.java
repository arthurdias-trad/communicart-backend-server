package br.com.communicart.backendserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.repository.VagaRepository;

@Repository
public class VagaService {
	@Autowired
	private VagaRepository vagaRepository;
	
	public Vaga create(Vaga vaga) {
		return vagaRepository.save(vaga);
	}
}
