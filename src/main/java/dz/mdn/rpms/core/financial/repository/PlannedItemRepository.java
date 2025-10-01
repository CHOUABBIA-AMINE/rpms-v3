/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: PlannedItemRepository
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

import dz.mdn.rpms.core.financial.domain.model.PlannedItem;

@RepositoryRestResource(collectionResourceRel = "plannedItem", path = "plannedItem")
public interface PlannedItemRepository extends JpaRepository<PlannedItem, Long> {
   
	@RestResource(rel ="filterBy", path = "filterBy")
    Page<PlannedItem> findByDesignationContaining(@Param("filter") String filter_01, Pageable page);
	
	@RestResource(rel ="inList", path = "inList")
    Page<PlannedItem> findByDesignationContainingOrFinancialOperation_OperationContainingOrItem_DesignationFrContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);

}