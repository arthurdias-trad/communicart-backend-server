package br.com.communicart.backendserver.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.model.dto.CreateVagaDto;
import br.com.communicart.backendserver.model.dto.VagaResponseDto;
import br.com.communicart.backendserver.model.entity.ContactFormsAvailableForJob;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.enums.StatusVaga;
import br.com.communicart.backendserver.model.enums.TipoServico;
import br.com.communicart.backendserver.repository.ContactFormsAvailableForJobRepository;
import br.com.communicart.backendserver.repository.VagaRepository;
import br.com.communicart.backendserver.security.JwtUtil;

@Service
public class VagaService {
	@Autowired
	private VagaRepository vagaRepository;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private ContactFormsAvailableForJobRepository contactFormsRepository;
	@Autowired
	private JwtUtil jwtUtil;

	public List<Vaga> listar() {
		return vagaRepository.findAll();
	}
	
	public List<Vaga> listarByStatus(StatusVaga statusVaga){
		return vagaRepository.findByStatusVaga(statusVaga);
	}
	
	public Vaga findVagaById (Long id) {
		return vagaRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Não foi possível encontrar vaga com id: " + id));
	}
	
	public List<Vaga> findVagasByPerfilId(Long id) {
		return vagaRepository.findByPerfilId(id);
	}
	
	@Transactional
	public Vaga create(Vaga vaga) {
		ContactFormsAvailableForJob contactForms = contactFormsRepository.save(vaga.getContactForms());
		vaga.setContactForms(contactForms);
		return vagaRepository.save(vaga);
	}
	
	@Transactional
	public void update(Vaga vaga) {
		vagaRepository.save(vaga);
	}

	@Transactional
	public Vaga toModel(@Valid CreateVagaDto vagaDto, String token) {
		Long perfilId = this.jwtUtil.getProfileId(token.substring(7));		
		
		Perfil perfil = this.perfilService.findById(perfilId);
		
		
		
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
				.fileURL(vagaDto.getFileURL())
				.paymentDate(vagaDto.getPaymentDate() == null ? null : new Date(vagaDto.getPaymentDate()))
				.build();
		
		return vaga;
	}
	
	@Transactional
	public VagaResponseDto toVagaResponseDto(Vaga vaga) {
		String jobOwner;
		
		
		
//		if (vaga.getPerfil().getPF() != null) {
//			jobOwner = vaga.getPerfil().getPF().getNomeCompleto();
//		} else {
//			jobOwner = vaga.getPerfil().getPJ().getNomeFantasia();
//		}
		if (vaga.getPerfil().getPF() != null) {
			jobOwner = vaga.getPerfil().getPF().getNomeCompleto();
		} else if (vaga.getPerfil().getPJ() != null) {
			jobOwner = vaga.getPerfil().getPJ().getNomeFantasia();
		} else {
			jobOwner = "";
		}

		
		String dateString;
		
		if (vaga.getPaymentDate() != null) {
			Format formatter = new SimpleDateFormat("dd-MM-yyyy");
			dateString = formatter.format(vaga.getPaymentDate());
		} else {
			dateString = null;
		}
		
		
		return VagaResponseDto.builder()
			.id(vaga.getId())
			.perfilId(vaga.getPerfil().getId())
			.titleJob(vaga.getTitleJob())
			.typeJob(vaga.getTypeJob().getTipo())
			.jobOwner(jobOwner)
			.description(vaga.getDescription())
			.price(vaga.getPrice())
			.paymentType(vaga.getPaymentType())
			.paymentToNegotiate(vaga.getPaymentToNegotiate())
			.paymentDate(dateString)
			.contactForms(vaga.getContactForms())
			.statusVaga(vaga.getStatusVaga())
			.fileURL(vaga.getFileURL())
			.build();
	}
}
