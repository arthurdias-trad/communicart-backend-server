package br.com.communicart.backendserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.enums.StatusVaga;

public interface VagaRepository extends JpaRepository<Vaga, Long>{

	List<Vaga> findByPerfilId(Long id);
	List<Vaga> findByStatusVaga(StatusVaga statusVaga);
}
