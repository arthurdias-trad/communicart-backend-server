package br.com.communicart.backendserver.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.model.dto.CandidaturaVagaDto;
import br.com.communicart.backendserver.model.dto.VagaResponseDto;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.entity.VagaCandidatura;
import br.com.communicart.backendserver.model.enums.StatusVaga;
import br.com.communicart.backendserver.repository.VagaCandidaturaRepository;

@Service
public class VagaCandidaturaService {
	@Autowired
	private VagaCandidaturaRepository vagaCandidaturaRepository;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private VagaService vagaService;
	
	public VagaCandidatura salvarCandidatura(Vaga vaga, Perfil perfil, CandidaturaVagaDto proposta) {
		if (this.vagaCandidaturaRepository.findByPerfilAndVaga(perfil, vaga) == null) {
			VagaCandidatura vagasCandidatura = VagaCandidatura.builder()
					.perfil(perfil)
					.vaga(vaga)
					.registeredAt(LocalDateTime.now())
					.price(proposta.getPrice())
					.observations(proposta.getObservations())
					.deliveryDate(proposta.getDeliveryDate())
					.build();
					
					return vagaCandidaturaRepository.save(vagasCandidatura);
		}
		return null;
	}
	
	public VagaCandidatura findById(Long id) {
		VagaCandidatura vagaCandidatura = vagaCandidaturaRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Candidatura não encontrada. Id da candidatura solicitada: "+id));
		
		return vagaCandidatura;
	}
	
	public Vaga selecionarCandidatoAVaga(Long vagaCandidaturaId) {
		VagaCandidatura vagaCandidatura = vagaCandidaturaRepository.findById(vagaCandidaturaId).orElseThrow(() -> new ObjectNotFoundException("Não foi possível encontrar essa relação candidato vaga."));
		
		Perfil candidato = vagaCandidatura.getPerfil();
		
		Vaga vaga = vagaService.findVagaById(vagaCandidatura.getVaga().getId());
		
		vaga.setSelectedFreelancer(candidato);
		vaga.setStatusVaga(StatusVaga.EM_ANDAMENTO);
		
		vagaService.update(vaga);
		
		return vaga;
	}

	public List<Perfil> findAllCandidatosByVagaId(Long vagaId) {
		Vaga vaga = vagaService.findVagaById(vagaId);
		
		
		List<VagaCandidatura> vagaCandidaturas = vagaCandidaturaRepository.findByVaga(vaga);
		List<Perfil> perfis = vagaCandidaturas.stream().map(mapper -> mapper.getPerfil()).collect(Collectors.toList());

		return perfis;
	}
	
	public VagaCandidatura listarProposta(Long idVagaCandidatura) {
		VagaCandidatura proposta = vagaCandidaturaRepository.findById(idVagaCandidatura)
				.orElseThrow(() -> new ObjectNotFoundException("Proposta não encontrada. Id da proposta: " + idVagaCandidatura));
	
		return proposta;
	}

	public void update(VagaCandidatura vagaCandidatura) {
		vagaCandidaturaRepository.save(vagaCandidatura);
	}

	public List<VagaCandidatura> findAllByVagaId(Long idVaga) {
		Vaga vaga = vagaService.findVagaById(idVaga);
		List<VagaCandidatura> candidaturasPorVaga = vagaCandidaturaRepository.findByVaga(vaga);
		return candidaturasPorVaga;
	}
	
	public List<VagaResponseDto> findByPerfilAndStatusVaga(Long perfilId, StatusVaga statusVaga) {
		Perfil perfil = this.perfilService.findById(perfilId);
		List<VagaCandidatura> candidaturas = this.vagaCandidaturaRepository.findByPerfil(perfil);

		List<VagaResponseDto> vagasDto = candidaturas.stream()
				.map(candidatura -> this.vagaService.toVagaResponseDto(candidatura.getVaga()))
				.filter(vaga -> vaga.getStatusVaga().equals(statusVaga))
				.collect(Collectors.toList());
		
		return vagasDto;
	}
}
