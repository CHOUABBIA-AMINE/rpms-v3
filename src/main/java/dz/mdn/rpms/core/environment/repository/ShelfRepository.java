/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ShelfRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Domain		: Environment
 *
 **/

package dz.mdn.rpms.core.environment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.environment.domain.model.Shelf;

@RepositoryRestResource(collectionResourceRel = "shelf", path = "shelf")
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
	@RestResource(path = "byRoom", rel = "byRoom")
    List<Shelf> findByRoom_Code(@Param("room") String room);

	@RestResource(rel ="inList", path = "inList")
    Page<Shelf> findByCodeContainingOrRoom_DesignationFrContainingOrRoom_CodeContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);
	

}