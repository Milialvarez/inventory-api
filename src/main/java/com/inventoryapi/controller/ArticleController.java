package com.inventoryapi.controller;

import com.inventoryapi.dto.ArticleDTO;
import com.inventoryapi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAll() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO articleDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> update(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        return ResponseEntity.ok(articleService.updateArticle(id, articleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}