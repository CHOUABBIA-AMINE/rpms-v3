/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: MilitaryRankRepository
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

import dz.mdn.rpms.core.administration.domain.model.MilitaryRank;

@RepositoryRestResource(collectionResourceRel = "militaryRank", path = "militaryRank")
public interface MilitaryRankRepository extends JpaRepository<MilitaryRank, Long> {
    
}