/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ConsultationPhaseRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.consultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.realization.consultation.domain.model.ConsultationPhase;

@RepositoryRestResource(collectionResourceRel = "consultationPhase", path = "consultationPhase")
public interface ConsultationPhaseRepository extends JpaRepository<ConsultationPhase, Long> {
	

}