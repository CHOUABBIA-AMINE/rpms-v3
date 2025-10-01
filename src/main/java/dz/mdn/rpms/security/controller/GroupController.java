package dz.mdn.rpms.security.controller;

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

import dz.mdn.rpms.security.domain.dto.GroupRequestDTO;
import dz.mdn.rpms.security.domain.dto.GroupResponseDTO;
import dz.mdn.rpms.security.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(@Valid @RequestBody GroupRequestDTO groupDto) {
        GroupResponseDTO createdGroup = groupService.createGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable Long id) {
        GroupResponseDTO group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupRequestDTO groupDto) {
        GroupResponseDTO updatedGroup = groupService.updateGroup(id, groupDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<GroupResponseDTO>> getAllGroups(
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<GroupResponseDTO> groups = groupService.getAllGroups(pageable);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDTO> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        GroupResponseDTO group = groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDTO> removeUserFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        GroupResponseDTO group = groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(group);
    }
}