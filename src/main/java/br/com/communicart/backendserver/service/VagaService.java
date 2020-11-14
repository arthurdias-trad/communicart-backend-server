package br.com.communicart.backendserver.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.model.dto.CreateVagaDto;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Servicos;
import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.enums.StatusVaga;
import br.com.communicart.backendserver.repository.PerfilRepository;
import br.com.communicart.backendserver.repository.VagaRepository;

@Repository
public class VagaService {
	@Autowired
	private VagaRepository vagaRepository;
	@Autowired
	private PerfilRepository perfilRepository;

	public List<Vaga> listar() {
		return vagaRepository.findAll();
	}
	
	public Vaga findVagaById (long id) {
		return vagaRepository.findById(id);
	}
	
	@Transactional
	public Vaga create(Vaga vaga) {
		return vagaRepository.save(vaga);
	}

	@Transactional
	public Vaga toModel(@Valid CreateVagaDto vagaDto) {
		Perfil perfil = perfilRepository.findById(vagaDto.getPerfilId())
				.orElseThrow(() -> new ObjectNotFoundException("Não foi possível encontrar perfil com id: \" + id"));
		Servicos servicos = Servicos.builder()
				.design(vagaDto.getTypeJob().isDesign())
				.edicao(vagaDto.getTypeJob().isEdicao())
				.fotografia(vagaDto.getTypeJob().isFotografia())
				.ilustracao(vagaDto.getTypeJob().isIlustracao())
				.redacao(vagaDto.getTypeJob().isRedacao())
				.build();
		
		Vaga vaga = Vaga.builder()
				.perfil(perfil)
				.titleJob(vagaDto.getTitleJob())
				.typeJob(servicos)
				.description(vagaDto.getDescription())
				.price(vagaDto.getPrice())
				.paymentType(vagaDto.getPaymentType())
				.paymentToNegotiate(vagaDto.getPaymentToNegotiate())
				.contactForms(vagaDto.getContactForms())
				.statusVaga(StatusVaga.ATIVA)
				.build();
		
		return vaga;
	}
}
