package br.com.communicart.backendserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.communicart.backendserver.model.entity.PessoaFisica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
	
}
