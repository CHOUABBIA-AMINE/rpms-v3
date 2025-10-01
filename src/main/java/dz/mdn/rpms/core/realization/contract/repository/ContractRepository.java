/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ContractRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.contract.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.realization.contract.domain.model.Contract;

@RepositoryRestResource(collectionResourceRel = "contract", path = "contract")
public interface ContractRepository extends JpaRepository<Contract, Long> {

	@RestResource(rel ="filterBy", path = "filterBy")
    Page<Contract> findByDesignationFrContainingOrReferenceContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, Pageable page);
	
	@RestResource(rel ="inList", path = "inList")
    Page<Contract> findByDesignationFrContainingOrReferenceContainingOrProvider_DesignationLtContainingOrProvider_AcronymLtContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, @Param("filter") String filter_04, Pageable page);
}