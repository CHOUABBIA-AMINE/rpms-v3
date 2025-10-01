/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ItemRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Financial
 *
 **/

package dz.mdn.rpms.core.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.core.financial.domain.model.Item;

@RepositoryRestResource(collectionResourceRel = "item", path = "item")
public interface ItemRepository extends JpaRepository<Item, Long> {
    
}