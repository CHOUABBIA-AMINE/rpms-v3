package dz.mdn.rpms.transversal.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.transversal.security.domain.model.Group;

@RepositoryRestResource(path = "groups")
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
    boolean existsByName(String name);
}