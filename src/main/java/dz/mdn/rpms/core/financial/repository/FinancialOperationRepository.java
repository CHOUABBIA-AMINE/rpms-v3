/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: FinancialOperationRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Financial
 *
 **/

package dz.mdn.rpms.core.financial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.financial.domain.model.FinancialOperation;

@RepositoryRestResource(collectionResourceRel = "financialOperation", path = "financialOperation")
public interface FinancialOperationRepository extends JpaRepository<FinancialOperation, Long> {
    
	@RestResource(rel ="inList", path = "inList")
    Page<FinancialOperation> findByOperationContainingOrBudgetYearContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, Pageable page);
}