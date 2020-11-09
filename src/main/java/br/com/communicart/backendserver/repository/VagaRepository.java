package br.com.communicart.backendserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.communicart.backendserver.model.entity.Vaga;

//@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long>{

}
