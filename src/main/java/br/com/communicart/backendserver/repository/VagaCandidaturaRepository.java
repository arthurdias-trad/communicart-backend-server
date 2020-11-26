package br.com.communicart.backendserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.communicart.backendserver.model.entity.VagasCandidaturas;

@Repository
public interface VagasCandidaturasRepository extends JpaRepository<VagasCandidaturas, Long>{

}
