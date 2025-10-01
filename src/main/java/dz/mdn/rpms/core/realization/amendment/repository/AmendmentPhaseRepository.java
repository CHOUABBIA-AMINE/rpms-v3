/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: AmendmentPhaseRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.amendment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.realization.amendment.domain.model.AmendmentPhase;

@RepositoryRestResource(collectionResourceRel = "amendmentPhase", path = "amendmentPhase")
public interface AmendmentPhaseRepository extends JpaRepository<AmendmentPhase, Long> {
	

}