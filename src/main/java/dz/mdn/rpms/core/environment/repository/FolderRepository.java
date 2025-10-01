/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: FolderRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Domain		: Environment
 *
 **/

package dz.mdn.rpms.core.environment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.environment.domain.model.Folder;

@RepositoryRestResource(collectionResourceRel = "folder", path = "folder")
public interface FolderRepository extends JpaRepository<Folder, Long> {
	
	@RestResource(rel ="inList", path = "inList")
    Page<Folder> findByCodeContainingOrDesignationFrContainingOrArchiveBox_CodeContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page); 

}