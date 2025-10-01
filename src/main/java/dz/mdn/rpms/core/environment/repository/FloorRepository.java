/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: FloorRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Domain		: Environment
 *
 **/

package dz.mdn.rpms.core.environment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.environment.domain.model.Floor;

@RepositoryRestResource(collectionResourceRel = "floor", path = "floor")
public interface FloorRepository extends JpaRepository<Floor, Long> {
    
}