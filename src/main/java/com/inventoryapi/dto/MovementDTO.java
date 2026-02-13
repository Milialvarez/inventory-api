package com.inventoryapi.dto;

import com.inventoryapi.enums.MovementType;
import java.time.LocalDateTime;

public class MovementDTO {
    private Long id;
    private LocalDateTime date;
    private Integer amount;
    private MovementType type;
    private Long articleId;
    private Long userId;

    public MovementDTO() {}

    public MovementDTO(Long id, LocalDateTime date, Integer amount, MovementType type, Long articleId, Long userId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.articleId = articleId;
        this.userId = userId;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }
    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}