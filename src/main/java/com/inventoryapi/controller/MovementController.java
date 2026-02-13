package com.inventoryapi.controller;

import com.inventoryapi.dto.MovementDTO;
import com.inventoryapi.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping
    public ResponseEntity<List<MovementDTO>> getAll() {
        return ResponseEntity.ok(movementService.getAllMovements());
    }

    @PostMapping
    public ResponseEntity<MovementDTO> register(@RequestBody MovementDTO movementDTO) {
        MovementDTO createdMovement = movementService.registerMovement(movementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovement);
    }
}