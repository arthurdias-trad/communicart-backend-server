package br.com.communicart.backendserver.service;

import org.springframework.stereotype.Service;

import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.model.dto.CreatePessoaDTO;
import br.com.communicart.backendserver.model.dto.UpdatePerfilDTO;
import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.PessoaFisica;
import br.com.communicart.backendserver.model.entity.PessoaJuridica;
import br.com.communicart.backendserver.repository.PerfilRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PerfilService {
	
	private final PerfilRepository perfilRepository;
	private final PessoaFisicaService pessoaFisicaService;
	private final PessoaJuridicaService pessoaJuridicaService;
	
	public Perfil findById(Long id) {
		return this.perfilRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Não foi possível encontrar perfil com id: " + id)); //personalizar erro
	}
	
	public Perfil update(Long id, UpdatePerfilDTO perfilDto) {
		Perfil perfilUsuario = this.findById(id);
		
		perfilUsuario.setInteresses(perfilDto.getInteresses());
		perfilUsuario.setBio(perfilDto.getBio());
		perfilUsuario.setWebsite(perfilDto.getWebsite());
		
		
		return this.perfilRepository.save(perfilUsuario);
	}
	
	public PessoaFisica createPessoaFísica(CreatePessoaDTO pessoaDto, Long id) {
		Perfil perfil = this.findById(id);
		PessoaFisica pf = pessoaFisicaFromDto(pessoaDto, perfil);
		
		pf = this.pessoaFisicaService.create(pf);
		perfil.setPJ(null);
		perfil.setPF(pf);
		
		this.perfilRepository.save(perfil);
		
		return pf;
	}
	
	public PessoaJuridica createPessoaJuridica (CreatePessoaDTO pessoaDto, Long id) {
		Perfil perfil = this.findById(id);
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
