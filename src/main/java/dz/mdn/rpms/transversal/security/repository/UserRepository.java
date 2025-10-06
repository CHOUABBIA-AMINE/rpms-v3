package dz.mdn.rpms.transversal.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dz.mdn.rpms.transversal.security.domain.model.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    //@PreAuthorize("hasRole('ADMIN')")
    List<User> findAll();
    
    Page<User> findAll(Pageable page);
    
    //@PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    Optional<User> findById(@Param("id") Long id);  
}