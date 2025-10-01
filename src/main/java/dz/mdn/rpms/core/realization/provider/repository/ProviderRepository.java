/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ProviderRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.realization.provider.domain.model.Provider;

@RepositoryRestResource(collectionResourceRel = "provider", path = "provider")
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    
	@RestResource(rel ="filterBy", path = "filterBy")
    Page<Provider> findByDesignationLtContainingOrAcronymLtContaining(@Param("filter") String designationFr, @Param("filter") String acronymLt, Pageable page);
    
	@RestResource(rel ="inList", path = "inList")
    Page<Provider> findByDesignationLtContainingOrAcronymLtContainingOrEconomicNature_DesignationFrContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);

}