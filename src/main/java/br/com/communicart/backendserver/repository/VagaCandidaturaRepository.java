package br.com.communicart.backendserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.communicart.backendserver.model.entity.Vaga;
import br.com.communicart.backendserver.model.entity.VagaCandidatura;

@Repository
public interface VagaCandidaturaRepository extends JpaRepository<VagaCandidatura, Long>{
//	@Query("SELECT v FROM vaga_candidatura v WHERE v.vaga_id=:vagaId and v.perfil_id=:perfilId")
//	VagaCandidatura findByIdAndPerfilId(@Param("vagaId") Long vagaId, @Param("perfilId") Long perfilId);
	
	List<VagaCandidatura> findByVaga(Vaga vaga);
	
	
}
