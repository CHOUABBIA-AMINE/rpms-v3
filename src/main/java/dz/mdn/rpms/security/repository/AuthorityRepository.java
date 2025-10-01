package dz.mdn.rpms.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.security.domain.model.Authority;

@RepositoryRestResource
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(String name);
    boolean existsByName(String name);
}