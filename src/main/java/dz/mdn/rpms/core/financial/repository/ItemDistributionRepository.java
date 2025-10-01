/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ItemDistributionRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Financial
 *
 **/

package dz.mdn.rpms.core.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.financial.domain.model.ItemDistribution;

@RepositoryRestResource(collectionResourceRel = "itemDistribution", path = "itemDistribution")
public interface ItemDistributionRepository extends JpaRepository<ItemDistribution, Long> {
    
}