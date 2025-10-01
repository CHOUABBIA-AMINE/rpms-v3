/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: MailTypeRepository
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

import dz.mdn.rpms.core.communication.domain.model.MailType;

@RepositoryRestResource(collectionResourceRel = "mailType", path = "mailType")
public interface MailTypeRepository extends JpaRepository<MailType, Long> {
    
}