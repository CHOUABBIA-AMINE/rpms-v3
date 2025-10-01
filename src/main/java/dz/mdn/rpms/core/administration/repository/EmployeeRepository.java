/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: EmployeeRepository
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Administration
 *
 **/

package dz.mdn.rpms.core.administration.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import dz.mdn.rpms.core.administration.domain.model.Employee;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@RestResource(path = "byStructure", rel = "byStructure")
    List<Employee> findByJob_Structure_DesignationFr(@Param("filter") String job_structure_designationFr);
	
	@RestResource(rel ="inList", path = "inList")
    Page<Employee> findBySerialContainingOrPerson_FirstnameLtContainingOrPerson_lastnameLtContaining(@Param("filter") String filter_01, @Param("filter") String filter_02, @Param("filter") String filter_03, Pageable page);
}