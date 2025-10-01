/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: EmpowermentRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.realization.provider.domain.model.Empowerment;

@RepositoryRestResource(collectionResourceRel = "empowerment", path = "empowerment")
public interface EmpowermentRepository extends JpaRepository<Empowerment, Long> {
    
}