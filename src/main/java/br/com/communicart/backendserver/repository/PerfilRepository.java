package br.com.communicart.backendserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.communicart.backendserver.model.entity.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	
}
