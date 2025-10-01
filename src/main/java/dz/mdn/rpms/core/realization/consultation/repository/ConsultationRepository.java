/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ConsultationRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.consultation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.realization.consultation.domain.model.Consultation;

@RepositoryRestResource(collectionResourceRel = "consultation", path = "consultation")
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
	
	@RestResource(rel ="filterBy", path = "filterBy")
    Page<Consultation> findByDesignationFrContainingOrReferenceContaining(@Param("filter") String designationFr, @Param("filter") String reference, Pageable page);
	
	@RestResource(rel ="inList", path = "inList")
    Page<Consultation> findByDesignationFrContainingOrReferenceContainingOrInternalIdContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);
}