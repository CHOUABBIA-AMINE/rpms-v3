/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ContractStepRepository
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

import dz.mdn.rpms.core.realization.contract.domain.model.ContractStep;

@RepositoryRestResource(collectionResourceRel = "contractStep", path = "contractStep")
public interface ContractStepRepository extends JpaRepository<ContractStep, Long> {
	

}