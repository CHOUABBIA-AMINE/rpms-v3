/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ProjectRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.realization.common.domain.model.Project;

@RepositoryRestResource(collectionResourceRel = "project", path = "project")
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
	@RestResource(rel ="filterBy", path = "filterBy")
    Page<Project> findByDesignationFrContainingOrInternalIdContainingOrProjectYearContaining(@Param("filter") String designationFr, @Param("filter") String internalId, @Param("filter") String projectYear, Pageable page);
    
}