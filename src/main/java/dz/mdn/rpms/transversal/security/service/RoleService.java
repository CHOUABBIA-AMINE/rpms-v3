package dz.mdn.rpms.transversal.security.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import dz.mdn.rpms.transversal.security.domain.dto.RoleRequestDTO;
import dz.mdn.rpms.transversal.security.domain.dto.RoleResponseDTO;
import dz.mdn.rpms.transversal.security.domain.model.Permission;
import dz.mdn.rpms.transversal.security.domain.model.Role;
import dz.mdn.rpms.transversal.security.repository.PermissionRepository;
import dz.mdn.rpms.transversal.security.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @PreAuthorize("hasPermission('ROLE', 'CREATE')")
    public RoleResponseDTO createRole(RoleRequestDTO roleDto) {
        if (roleRepository.existsByName(roleDto.name())) {
            throw new IllegalArgumentException("Role name already exists");
        }

        Role role = new Role();
        role.setName(roleDto.name());
        role.setDescription(roleDto.description());
        
        setPermissions(role, roleDto.permissionIds());
        
        Role savedRole = roleRepository.save(role);
        return RoleResponseDTO.fromEntity(savedRole);
    }

    @PreAuthorize("hasPermission(#id, 'ROLE', 'READ')")
    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return RoleResponseDTO.fromEntity(role);
    }

    @PreAuthorize("hasPermission(#id, 'ROLE', 'UPDATE')")
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        if (!role.getName().equals(roleDto.name()) && roleRepository.existsByName(roleDto.name())) {
            throw new IllegalArgumentException("Role name already exists");
        }

        role.setName(roleDto.name());
        role.setDescription(roleDto.description());
        
        setPermissions(role, roleDto.permissionIds());
        
        Role updatedRole = roleRepository.save(role);
        return RoleResponseDTO.fromEntity(updatedRole);
    }

    @PreAuthorize("hasPermission(#id, 'ROLE', 'DELETE')")
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }
        
        // Check if role is assigned to any users before deletion
        // You might want to add this check depending on your requirements
        roleRepository.deleteById(id);
    }

    @PreAuthorize("hasPermission('ROLE', 'READ')")
    public Page<RoleResponseDTO> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(RoleResponseDTO::fromEntity);
    }

    private void setPermissions(Role role, Set<Long> permissionIds) {
        role.getPermissions().clear();
        if (permissionIds != null && !permissionIds.isEmpty()) {
            Set<Permission> permissions = permissionRepository.findAllById(permissionIds).stream()
                    .collect(Collectors.toSet());
            role.getPermissions().addAll(permissions);
        }
    }
}