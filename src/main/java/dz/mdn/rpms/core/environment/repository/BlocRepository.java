/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: BlocRepository
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

import dz.mdn.rpms.core.environment.domain.model.Bloc;

@RepositoryRestResource(collectionResourceRel = "bloc", path = "bloc")
public interface BlocRepository extends JpaRepository<Bloc, Long> {
    
}