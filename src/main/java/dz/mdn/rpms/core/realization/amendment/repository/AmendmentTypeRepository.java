/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: AmendmentTypeRepository
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

import dz.mdn.rpms.core.realization.amendment.domain.model.AmendmentType;

@RepositoryRestResource(collectionResourceRel = "amendmentType", path = "amendmentType")
public interface AmendmentTypeRepository extends JpaRepository<AmendmentType, Long> {
	

}