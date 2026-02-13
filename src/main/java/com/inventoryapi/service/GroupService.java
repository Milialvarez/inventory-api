package com.inventoryapi.service;

import com.inventoryapi.dto.GroupDTO;
import com.inventoryapi.entity.Group;
import com.inventoryapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return convertToDTO(group);
    }

    @Transactional
    public GroupDTO createGroup(GroupDTO dto) {
        Group group = new Group();
        group.setName(dto.getName());

        Group savedGroup = groupRepository.save(group);
        return convertToDTO(savedGroup);
    }

    @Transactional
    public GroupDTO updateGroup(Long id, GroupDTO dto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        group.setName(dto.getName());

        Group updatedGroup = groupRepository.save(group);
        return convertToDTO(updatedGroup);
    }

    @Transactional
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found with id: " + id);
        }
        groupRepository.deleteById(id);
    }

    private GroupDTO convertToDTO(Group group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        return dto;
    }
}