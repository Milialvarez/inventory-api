package com.inventoryapi.service;

import com.inventoryapi.dto.MovementDTO;
import com.inventoryapi.entity.Article;
import com.inventoryapi.entity.Movement;
import com.inventoryapi.entity.User;
import com.inventoryapi.enums.MovementType;
import com.inventoryapi.repository.ArticleRepository;
import com.inventoryapi.repository.MovementRepository;
import com.inventoryapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public MovementDTO registerMovement(MovementDTO dto) {

        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + dto.getArticleId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        if (dto.getType() == MovementType.SALIDA) {
            if (article.getStock() < dto.getAmount()) {
                throw new RuntimeException("Insufficient stock. Current stock: " + article.getStock());
            }
            article.setStock(article.getStock() - dto.getAmount());
        } else if (dto.getType() == MovementType.ENTRADA) {
            article.setStock(article.getStock() + dto.getAmount());
        } else {
            throw new RuntimeException("Invalid movement type");
        }

        Movement movement = new Movement();
        movement.setAmount(dto.getAmount());
        movement.setType(dto.getType());
        movement.setDate(LocalDateTime.now());
        movement.setArticle(article);
        movement.setUser(user);

        articleRepository.save(article);
        Movement savedMovement = movementRepository.save(movement);

        return convertToDTO(savedMovement);
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> getAllMovements() {
        return movementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MovementDTO convertToDTO(Movement movement) {
        MovementDTO dto = new MovementDTO();
        dto.setId(movement.getId());
        dto.setDate(movement.getDate());
        dto.setAmount(movement.getAmount());
        dto.setType(movement.getType());
        dto.setArticleId(movement.getArticle().getId());
        dto.setUserId(movement.getUser().getId());
        return dto;
    }
}