/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: BudgetModificationRepository
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

import dz.mdn.rpms.core.financial.domain.model.BudgetModification;

@RepositoryRestResource(collectionResourceRel = "budgetModification", path = "budgetModification")
public interface BudgetModificationRepository extends JpaRepository<BudgetModification, Long> {
    
}