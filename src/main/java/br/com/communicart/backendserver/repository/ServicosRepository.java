package br.com.communicart.backendserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.communicart.backendserver.model.entity.Servicos;

public interface ServicosRepository extends JpaRepository<Servicos, Long> {
	
}
