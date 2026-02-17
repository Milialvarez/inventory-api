package com.inventoryapi.service;

import com.inventoryapi.dto.MovementDTO;
import com.inventoryapi.entity.Article;
import com.inventoryapi.entity.Movement;
import com.inventoryapi.entity.User;
import com.inventoryapi.enums.MovementType;
import com.inventoryapi.exception.InsufficientStockException;
import com.inventoryapi.exception.InvalidDataException;
import com.inventoryapi.exception.ResourceNotFoundException;
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

        // Usamos ResourceNotFoundException
        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + dto.getArticleId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        if (dto.getType() == MovementType.SALIDA) {
            if (article.getStock() < dto.getAmount()) {
                // Usamos InsufficientStockException
                throw new InsufficientStockException("Insufficient stock. Current stock: " + article.getStock() + ", Requested: " + dto.getAmount());
            }
            article.setStock(article.getStock() - dto.getAmount());
        } else if (dto.getType() == MovementType.ENTRADA) {
            article.setStock(article.getStock() + dto.getAmount());
        } else {
            // Usamos InvalidDataException
            throw new InvalidDataException("Invalid movement type: " + dto.getType());
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
    public List<MovementDTO> getMovementsByArticleId(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            // Usamos ResourceNotFoundException
            throw new ResourceNotFoundException("Article not found with id: " + articleId);
        }

        return movementRepository.findByArticleId(articleId).stream()
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