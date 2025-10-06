package dz.mdn.rpms.transversal.security.controller;

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

import dz.mdn.rpms.transversal.security.domain.dto.AuthorityRequestDTO;
import dz.mdn.rpms.transversal.security.domain.dto.AuthorityResponseDTO;
import dz.mdn.rpms.transversal.security.service.AuthorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping
    public ResponseEntity<AuthorityResponseDTO> createAuthority(@Valid @RequestBody AuthorityRequestDTO authorityDto) {
        AuthorityResponseDTO createdAuthority = authorityService.createAuthority(authorityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthority);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorityResponseDTO> getAuthorityById(@PathVariable Long id) {
        AuthorityResponseDTO authority = authorityService.getAuthorityById(id);
        return ResponseEntity.ok(authority);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorityResponseDTO> updateAuthority(
            @PathVariable Long id,
            @Valid @RequestBody AuthorityRequestDTO authorityDto) {
        AuthorityResponseDTO updatedAuthority = authorityService.updateAuthority(id, authorityDto);
        return ResponseEntity.ok(updatedAuthority);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        authorityService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AuthorityResponseDTO>> getAllAuthorities(
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AuthorityResponseDTO> authorities = authorityService.getAllAuthorities(pageable);
        return ResponseEntity.ok(authorities);
    }

    @PostMapping("/{authorityId}/permissions/{permissionId}")
    public ResponseEntity<AuthorityResponseDTO> addPermissionToAuthority(
            @PathVariable Long authorityId,
            @PathVariable Long permissionId) {
        AuthorityResponseDTO authority = authorityService.addPermissionToAuthority(authorityId, permissionId);
        return ResponseEntity.ok(authority);
    }

    @DeleteMapping("/{authorityId}/permissions/{permissionId}")
    public ResponseEntity<AuthorityResponseDTO> removePermissionFromAuthority(
            @PathVariable Long authorityId,
            @PathVariable Long permissionId) {
        AuthorityResponseDTO authority = authorityService.removePermissionFromAuthority(authorityId, permissionId);
        return ResponseEntity.ok(authority);
    }
}