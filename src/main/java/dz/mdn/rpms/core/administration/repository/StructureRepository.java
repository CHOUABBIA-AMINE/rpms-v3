/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: StructureRepository
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

import dz.mdn.rpms.core.administration.domain.model.Structure;

@RepositoryRestResource(collectionResourceRel = "structure", path = "structure")
public interface StructureRepository extends JpaRepository<Structure, Long> {
	
	@RestResource(rel ="filterBy", path = "filterBy")
    Page<Structure> findByDesignationFrContainingOrAcronymFrContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, Pageable page);

	@RestResource(rel ="inList", path = "inList")
    Page<Structure> findByDesignationFrContainingOrAcronymFrContainingOrStructureType_DesignationFrContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);
}
