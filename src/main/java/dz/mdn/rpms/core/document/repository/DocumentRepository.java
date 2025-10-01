/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: DocumentRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Document
 *
 **/

package dz.mdn.rpms.core.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.document.domain.model.Document;

@RepositoryRestResource(collectionResourceRel = "document", path = "document")
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
	//@RestResource(rel ="filterBy", path = "filterBy")
    //Page<Document> findByDesignationFrContaining(@Param("filter") String designationFr, Pageable page);
}