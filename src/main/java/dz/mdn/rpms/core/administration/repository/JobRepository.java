/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: JobRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Administration
 *
 **/

package dz.mdn.rpms.core.administration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.administration.domain.model.Job;

@RepositoryRestResource(collectionResourceRel = "job", path = "job")
public interface JobRepository extends JpaRepository<Job, Long> {
	
	@RestResource(rel ="filterBy", path = "filterBy")
    Page<Job> findByDesignationFrContainingAndStructure_DesignationFrOrDesignationFrContainingAndStructure_StructureUp_DesignationFr(@Param("job") String param1, @Param("structure") String param2, @Param("job") String param3, @Param("structure") String param4, Pageable page);

	@RestResource(rel ="inList", path = "inList")
    Page<Job> findByDesignationFrContainingOrStructure_DesignationFrContainingOrStructure_AcronymFrContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);
}