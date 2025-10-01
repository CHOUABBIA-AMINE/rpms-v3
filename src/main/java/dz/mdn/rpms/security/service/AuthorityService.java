package dz.mdn.rpms.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import dz.mdn.rpms.security.domain.dto.AuthorityRequestDTO;
import dz.mdn.rpms.security.domain.dto.AuthorityResponseDTO;
import dz.mdn.rpms.security.domain.model.Authority;
import dz.mdn.rpms.security.domain.model.Permission;
import dz.mdn.rpms.security.repository.AuthorityRepository;
import dz.mdn.rpms.security.repository.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final PermissionRepository permissionRepository;

    @PreAuthorize("hasPermission('AUTHORITY', 'CREATE')")
    public AuthorityResponseDTO createAuthority(AuthorityRequestDTO authorityDto) {
        if (authorityRepository.existsByName(authorityDto.name())) {
            throw new IllegalArgumentException("Authority name already exists");
        }

        Authority authority = new Authority();
        authority.setName(authorityDto.name());
        authority.setDescription(authorityDto.description());
        
        Authority savedAuthority = authorityRepository.save(authority);
        return AuthorityResponseDTO.fromEntity(savedAuthority);
    }

    @PreAuthorize("hasPermission(#id, 'AUTHORITY', 'READ')")
    public AuthorityResponseDTO getAuthorityById(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authority not found with id: " + id));
        return AuthorityResponseDTO.fromEntity(authority);
    }

    @PreAuthorize("hasPermission(#id, 'AUTHORITY', 'UPDATE')")
    public AuthorityResponseDTO updateAuthority(Long id, AuthorityRequestDTO authorityDto) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authority not found with id: " + id));

        if (!authority.getName().equals(authorityDto.name()) && authorityRepository.existsByName(authorityDto.name())) {
            throw new IllegalArgumentException("Authority name already exists");
        }

        authority.setName(authorityDto.name());
        authority.setDescription(authorityDto.description());
        
        Authority updatedAuthority = authorityRepository.save(authority);
        return AuthorityResponseDTO.fromEntity(updatedAuthority);
    }

    @PreAuthorize("hasPermission(#id, 'AUTHORITY', 'DELETE')")
    public void deleteAuthority(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authority not found with id: " + id));
        
        // Check if authority has permissions before deletion
        if (!authority.getPermissions().isEmpty()) {
            throw new IllegalStateException("Cannot delete authority with assigned permissions");
        }
        
        authorityRepository.delete(authority);
    }

    @PreAuthorize("hasPermission('AUTHORITY', 'READ')")
    public Page<AuthorityResponseDTO> getAllAuthorities(Pageable pageable) {
        return authorityRepository.findAll(pageable)
                .map(AuthorityResponseDTO::fromEntity);
    }

    @PreAuthorize("hasPermission(#authorityId, 'AUTHORITY', 'READ')")
    public AuthorityResponseDTO addPermissionToAuthority(Long authorityId, Long permissionId) {
        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new EntityNotFoundException("Authority not found with id: " + authorityId));
        
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + permissionId));
        
        permission.setAuthority(authority);
        authority.getPermissions().add(permission);
        
        authorityRepository.save(authority);
        return AuthorityResponseDTO.fromEntity(authority);
    }

    @PreAuthorize("hasPermission(#authorityId, 'AUTHORITY', 'UPDATE')")
    public AuthorityResponseDTO removePermissionFromAuthority(Long authorityId, Long permissionId) {
        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new EntityNotFoundException("Authority not found with id: " + authorityId));
        
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + permissionId));
        
        if (!authority.getPermissions().contains(permission)) {
            throw new IllegalStateException("Permission not assigned to this authority");
        }
        
        permission.setAuthority(null);
        authority.getPermissions().remove(permission);
        
        authorityRepository.save(authority);
        return AuthorityResponseDTO.fromEntity(authority);
    }
}