package br.com.communicart.backendserver.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.model.dto.CandidaturaVagaDto;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.entity.VagasCandidaturas;
import br.com.communicart.backendserver.repository.VagasCandidaturasRepository;

@Service
public class VagasCandidaturasService {
	@Autowired
	private VagasCandidaturasRepository vagasCandidaturasRepository;
	
	public VagasCandidaturas salvarCandidatura(Vaga vaga, Perfil perfil, CandidaturaVagaDto proposta) {
		VagasCandidaturas vagasCandidaturas = VagasCandidaturas.builder()
		.perfil(perfil)
		.vaga(vaga)
		.registeredAt(LocalDateTime.now())
		.price(proposta.getPrice())
		.observations(proposta.getObservations())
		.deliveryDate(proposta.getDeliveryDate())
		.build();
		
		return vagasCandidaturasRepository.save(vagasCandidaturas);
	}
}
