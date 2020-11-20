package br.com.communicart.backendserver.service;

import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.communicart.backendserver.exception.DataIntegrityException;
import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.model.dto.CreatePessoaDTO;
import br.com.communicart.backendserver.model.dto.UpdatePerfilDTO;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.PessoaFisica;
import br.com.communicart.backendserver.model.entity.PessoaJuridica;
import br.com.communicart.backendserver.repository.PerfilRepository;
import br.com.communicart.backendserver.security.JwtUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PerfilService {
	
	private final PerfilRepository perfilRepository;
	private final PessoaFisicaService pessoaFisicaService;
	private final PessoaJuridicaService pessoaJuridicaService;
	private final JwtUtil jwtUtil;
	
	public Perfil findById(Long id) {
		return this.perfilRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Não foi possível encontrar perfil com id: " + id)); 
	}
	
	public boolean validateId(Long id, String header) {
		String jwt = header.substring(7);
		
		return id.equals(jwtUtil.getProfileId(jwt));
	}
	
	@Transactional
	public Perfil update(Long id, UpdatePerfilDTO perfilDto) {
		Perfil perfilUsuario = this.findById(id);
		
		if (perfilUsuario.getPF() == null && perfilUsuario.getPJ() == null) {
			throw new DataIntegrityException("Para criar um perfil, o usuário deve ter uma PF ou PJ cadastrada");
		}
		
		if(perfilUsuario.getServicos() != null) {
			perfilDto.getServicos().setId(perfilUsuario.getServicos().getId());
		}
		
		if (perfilUsuario.getMidiasSociais() != null) {
			perfilDto.getMidiasSociais().setId(perfilUsuario.getMidiasSociais().getId());
		}
		
		perfilUsuario.setInteresses(perfilDto.getInteresses());
		perfilUsuario.setBio(perfilDto.getBio());
		perfilUsuario.setWebsite(perfilDto.getWebsite());
		perfilUsuario.setMidiasSociais(perfilDto.getMidiasSociais());
		perfilUsuario.setServicos(perfilDto.getServicos());
		
		return this.perfilRepository.save(perfilUsuario);
	}
	
	@Transactional
	public void saveImage(String header, URL imageURL) {
		Perfil perfilUsuario = this.findById(jwtUtil.getProfileId(header.substring(7)));
		
		perfilUsuario.setImageURL(imageURL);
		
		this.perfilRepository.save(perfilUsuario);
	}
	
	@Transactional
	public PessoaFisica createPessoaFísica(CreatePessoaDTO pessoaDto, Long id) {
		Perfil perfil = this.findById(id);
		
		if(perfil.hasPessoa()) {
			throw new DataIntegrityException("O perfil já tem uma pessoa física ou jurídica associada");
		}
		
		PessoaFisica pf = pessoaFisicaFromDto(pessoaDto, perfil);
		
		pf = this.pessoaFisicaService.create(pf);
		perfil.setPJ(null);
		perfil.setPF(pf);
		
		this.perfilRepository.save(perfil);
		
		return pf;
	}
	@Transactional
	public PessoaJuridica createPessoaJuridica (CreatePessoaDTO pessoaDto, Long id) {
		Perfil perfil = this.findById(id);
		
		if(perfil.hasPessoa()) {
			throw new DataIntegrityException("O perfil já tem uma pessoa física ou jurídica associada");
		}
		
		PessoaJuridica pj = pessoaJuridicaFromDto(pessoaDto, perfil);
		
		pj = this.pessoaJuridicaService.create(pj);
		perfil.setPJ(pj);
		perfil.setPF(null);
		
		this.perfilRepository.save(perfil);
		
		return pj;
	}
	
	public PessoaFisica pessoaFisicaFromDto(CreatePessoaDTO pessoaDto, Perfil perfil) {
		return PessoaFisica.builder()
			.nome(pessoaDto.getNome())
			.sobrenome(pessoaDto.getSobrenome())
			.cpf(pessoaDto.getCpf())
			.perfil(perfil)
			.build();
	}
	
	public PessoaJuridica pessoaJuridicaFromDto(CreatePessoaDTO pessoaDto, Perfil perfil) {
		return PessoaJuridica.builder()
			.cnpj(pessoaDto.getCnpj())
			.nomeFantasia(pessoaDto.getNomeFantasia())
			.nomeRepresentante(pessoaDto.getNomeRepresentante())
			.perfil(perfil)
			.build();
	}
	

}
