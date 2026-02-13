package com.inventoryapi.entity;

import com.inventoryapi.enums.MovementType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
public class Movement {

    public Movement(LocalDateTime date, Integer amount, MovementType type, Article article, User user) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.article = article;
        this.user = user;
    }

    public Movement() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private Integer amount;

    @Enumerated(EnumType.STRING) //
    private MovementType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // who registered the movement
}
