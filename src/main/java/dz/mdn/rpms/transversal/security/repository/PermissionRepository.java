package dz.mdn.rpms.transversal.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.transversal.security.domain.model.Permission;

@RepositoryRestResource(path = "permissions")
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
    List<Permission> findByAuthorityId(Long authorityId);
}