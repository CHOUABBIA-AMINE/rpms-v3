/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: MailRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Communication
 *
 **/

package dz.mdn.rpms.core.communication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.communication.domain.model.Mail;

@RepositoryRestResource(collectionResourceRel = "mail", path = "mail")
public interface MailRepository extends JpaRepository<Mail, Long> {
	@RestResource(path = "filterBy", rel = "filterBy")
	Page<Mail> findByReferenceContainingOrSubjectContainingOrStructure_DesignationFrContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable pageable);
}