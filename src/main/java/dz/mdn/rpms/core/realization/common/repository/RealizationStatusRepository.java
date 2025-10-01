/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: RealizationStatusRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.realization.common.domain.model.RealizationStatus;

@RepositoryRestResource(collectionResourceRel = "realizationStatus", path = "realizationStatus")
public interface RealizationStatusRepository extends JpaRepository<RealizationStatus, Long> {
    
}