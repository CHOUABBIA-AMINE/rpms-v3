/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: EconomicNatureRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.realization.provider.domain.model.EconomicNature;

@RepositoryRestResource(collectionResourceRel = "economicNature", path = "economicNature")
public interface EconomicNatureRepository extends JpaRepository<EconomicNature, Long> {
    
}