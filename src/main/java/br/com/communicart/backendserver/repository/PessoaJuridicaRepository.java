package br.com.communicart.backendserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.communicart.backendserver.model.entity.PessoaJuridica;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
	
}
