/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: MailNatureRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Communication
 *
 **/

package dz.mdn.rpms.core.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.communication.domain.model.MailNature;

@RepositoryRestResource(collectionResourceRel = "mailNature", path = "mailNature")
public interface MailNatureRepository extends JpaRepository<MailNature, Long> {
    
}