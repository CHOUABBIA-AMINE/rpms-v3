/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: AmendmentRepository
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

import dz.mdn.rpms.core.realization.amendment.domain.model.Amendment;

@RepositoryRestResource(collectionResourceRel = "amendment", path = "amendment")
public interface AmendmentRepository extends JpaRepository<Amendment, Long> {
	

}