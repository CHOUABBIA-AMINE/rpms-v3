/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ContractTypeRepository
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

import dz.mdn.rpms.core.realization.contract.domain.model.ContractType;

@RepositoryRestResource(collectionResourceRel = "contractType", path = "contractType")
public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
	

}