/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: SubmissionRepository
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

import dz.mdn.rpms.core.realization.consultation.domain.model.Submission;

@RepositoryRestResource(collectionResourceRel = "submission", path = "submission")
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
	
	@RestResource(rel ="filterBy", path = "filterBy")
    Page<Submission> findByfinancialOfferLessThan(@Param("filter") double filter, Pageable page);
	
	@RestResource(rel ="inList", path = "inList")
    Page<Submission> findByfinancialOfferLessThanEqual(@Param("filter") double filter, Pageable page);
}