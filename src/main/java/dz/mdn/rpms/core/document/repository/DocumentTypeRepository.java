/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: DocumentTypeRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Document
 *
 **/

package dz.mdn.rpms.core.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.document.domain.model.DocumentType;

@RepositoryRestResource(collectionResourceRel = "documentType", path = "documentType")
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    
	//@RestResource(rel ="filterBy", path = "filterBy")
    //Page<DocumentType> findByDesignationFrContaining(@Param("filter") String designationFr, Pageable page);
	
	@RestResource(rel ="filterByScope", path = "filterByScope")
    List<DocumentType> findByScope(@Param("filter") int filter_01);
}