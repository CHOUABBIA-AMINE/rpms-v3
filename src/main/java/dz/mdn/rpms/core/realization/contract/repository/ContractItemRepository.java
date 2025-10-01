/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ContractItemRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.realization.contract.domain.model.ContractItem;

@RepositoryRestResource(collectionResourceRel = "contractItem", path = "contractItem")
public interface ContractItemRepository extends JpaRepository<ContractItem, Long> {

}