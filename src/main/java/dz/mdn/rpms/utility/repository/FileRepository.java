/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: FileRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Utility
 *
 **/

package dz.mdn.rpms.utility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.utility.domain.model.File;

@RepositoryRestResource(collectionResourceRel = "file", path = "file")
public interface FileRepository extends JpaRepository<File, Long> {
    
}