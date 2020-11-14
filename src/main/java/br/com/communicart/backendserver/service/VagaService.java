package br.com.communicart.backendserver.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.model.dto.CreateVagaDto;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.enums.StatusVaga;
import br.com.communicart.backendserver.model.enums.TipoServico;
import br.com.communicart.backendserver.repository.PerfilRepository;
import br.com.communicart.backendserver.repository.VagaRepository;
import br.com.communicart.backendserver.security.JwtUtil;

@Repository
public class VagaService {
	@Autowired
	private VagaRepository vagaRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private JwtUtil jwtUtil;

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
	public Vaga toModel(@Valid CreateVagaDto vagaDto, String token) {
		Long perfilId = jwtUtil.getProfileId(token.substring(7));		
		
		Perfil perfil = perfilRepository.findById(perfilId)
				.orElseThrow(() -> new ObjectNotFoundException("Não foi possível encontrar perfil com id: \" + id"));
		
		
		Vaga vaga = Vaga.builder()
				.perfil(perfil)
				.titleJob(vagaDto.getTitleJob())
				.typeJob(TipoServico.toEnum(vagaDto.getTypeJob()))
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
