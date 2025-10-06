package dz.mdn.rpms.transversal.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import dz.mdn.rpms.transversal.security.domain.dto.PermissionRequestDTO;
import dz.mdn.rpms.transversal.security.domain.dto.PermissionResponseDTO;
import dz.mdn.rpms.transversal.security.domain.model.Authority;
import dz.mdn.rpms.transversal.security.domain.model.Permission;
import dz.mdn.rpms.transversal.security.repository.AuthorityRepository;
import dz.mdn.rpms.transversal.security.repository.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final AuthorityRepository authorityRepository;

    @PreAuthorize("hasPermission('PERMISSION', 'CREATE')")
    public PermissionResponseDTO createPermission(PermissionRequestDTO permissionDto) {
        if (permissionRepository.existsByName(permissionDto.name())) {
            throw new IllegalArgumentException("Permission name already exists");
        }

        Permission permission = new Permission();
        permission.setName(permissionDto.name());
        permission.setDescription(permissionDto.description());
        
        setAuthority(permission, permissionDto.authorityId());
        
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionResponseDTO.fromEntity(savedPermission);
    }

    @PreAuthorize("hasPermission(#id, 'PERMISSION', 'READ')")
    public PermissionResponseDTO getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        return PermissionResponseDTO.fromEntity(permission);
    }

    @PreAuthorize("hasPermission(#id, 'PERMISSION', 'UPDATE')")
    public PermissionResponseDTO updatePermission(Long id, PermissionRequestDTO permissionDto) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));

        if (!permission.getName().equals(permissionDto.name()) && permissionRepository.existsByName(permissionDto.name())) {
            throw new IllegalArgumentException("Permission name already exists");
        }

        permission.setName(permissionDto.name());
        permission.setDescription(permissionDto.description());
        
        setAuthority(permission, permissionDto.authorityId());
        
        Permission updatedPermission = permissionRepository.save(permission);
        return PermissionResponseDTO.fromEntity(updatedPermission);
    }

    @PreAuthorize("hasPermission(#id, 'PERMISSION', 'DELETE')")
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        
        // Check if permission is assigned to any roles before deletion
        if (!permission.getRoles().isEmpty()) { // Assuming you have a roles relationship in Permission
            throw new IllegalStateException("Cannot delete permission assigned to roles");
        }
        
        permissionRepository.delete(permission);
    }

    @PreAuthorize("hasPermission('PERMISSION', 'READ')")
    public Page<PermissionResponseDTO> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(PermissionResponseDTO::fromEntity);
    }

    @PreAuthorize("hasPermission('PERMISSION', 'READ')")
    public List<PermissionResponseDTO> getPermissionsByAuthority(Long authorityId) {
        return permissionRepository.findByAuthorityId(authorityId).stream()
                .map(PermissionResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private void setAuthority(Permission permission, Long authorityId) {
        if (authorityId == null) {
            permission.setAuthority(null);
        } else {
            Authority authority = authorityRepository.findById(authorityId)
                    .orElseThrow(() -> new EntityNotFoundException("Authority not found with id: " + authorityId));
            permission.setAuthority(authority);
        }
    }
}