/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: StructureTypeRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Administration
 *
 **/

package dz.mdn.rpms.core.administration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.administration.domain.model.StructureType;

@RepositoryRestResource(collectionResourceRel = "structureType", path = "structureType")
public interface StructureTypeRepository extends JpaRepository<StructureType, Long> {
    
}