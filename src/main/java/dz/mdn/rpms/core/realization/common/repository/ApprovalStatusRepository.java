/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ApprovalStatusRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.realization.common.domain.model.ApprovalStatus;

@RepositoryRestResource(collectionResourceRel = "approvalStatus", path = "approvalStatus")
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Long> {
    
}