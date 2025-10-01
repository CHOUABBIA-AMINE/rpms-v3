package dz.mdn.rpms.security.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.mdn.rpms.security.domain.dto.PermissionRequestDTO;
import dz.mdn.rpms.security.domain.dto.PermissionResponseDTO;
import dz.mdn.rpms.security.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionResponseDTO> createPermission(@Valid @RequestBody PermissionRequestDTO permissionDto) {
        PermissionResponseDTO createdPermission = permissionService.createPermission(permissionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable Long id) {
        PermissionResponseDTO permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionRequestDTO permissionDto) {
        PermissionResponseDTO updatedPermission = permissionService.updatePermission(id, permissionDto);
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<PermissionResponseDTO>> getAllPermissions(
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PermissionResponseDTO> permissions = permissionService.getAllPermissions(pageable);
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/by-authority/{authorityId}")
    public ResponseEntity<List<PermissionResponseDTO>> getPermissionsByAuthority(@PathVariable Long authorityId) {
        List<PermissionResponseDTO> permissions = permissionService.getPermissionsByAuthority(authorityId);
        return ResponseEntity.ok(permissions);
    }
}