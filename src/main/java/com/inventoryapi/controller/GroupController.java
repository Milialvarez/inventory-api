package com.inventoryapi.controller;

import com.inventoryapi.dto.GroupDTO;
import com.inventoryapi.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAll() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GroupDTO> create(@RequestBody GroupDTO groupDTO) {
        GroupDTO createdGroup = groupService.createGroup(groupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> update(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}