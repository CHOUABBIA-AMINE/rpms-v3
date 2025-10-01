/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: RoomRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Domain		: Environment
 *
 **/

package dz.mdn.rpms.core.environment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.environment.domain.model.Room;

@RepositoryRestResource(collectionResourceRel = "room", path = "room")
public interface RoomRepository extends JpaRepository<Room, Long> {
	
	@RestResource(path = "byBlocAndFloor", rel = "byBlocAndFloor")
    List<Room> findByBloc_CodeLtAndFloor_Code(@Param("bloc") String bloc, @Param("floor") String floor);
	
	@RestResource(rel ="inList", path = "inList")
    Page<Room> findByDesignationFrContainingOrCodeContainingOrStructure_DesignationFrContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);
	
	@RestResource(rel ="get", path = "get")
    Page<Room> findByBloc_IdAndFloor_IdAndStructure_Id(@Param("bloc") Long filter_01, @Param("floor") Long filter_02, @Param("structure") Long filter_03, Pageable page);
}