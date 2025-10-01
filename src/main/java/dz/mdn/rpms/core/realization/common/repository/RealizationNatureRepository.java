/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: RealizationNatureRepository
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

import dz.mdn.rpms.core.realization.common.domain.model.RealizationNature;

@RepositoryRestResource(collectionResourceRel = "realizationNature", path = "realizationNature")
public interface RealizationNatureRepository extends JpaRepository<RealizationNature, Long> {
    
}