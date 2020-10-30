package br.com.communicart.backendserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.communicart.backendserver.model.entity.MidiasSociais;

public interface MidiasSociaisRepository extends JpaRepository<MidiasSociais, Long> {
	
}
