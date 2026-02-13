package com.inventoryapi.service;

import com.inventoryapi.dto.ArticleDTO;
import com.inventoryapi.entity.Article;
import com.inventoryapi.entity.Group;
import com.inventoryapi.repository.ArticleRepository;
import com.inventoryapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private GroupRepository groupRepository;

    // GET ALL
    @Transactional(readOnly = true) // Optimiza la consulta en base de datos
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));
        return convertToDTO(article);
    }

    // CREATE (POST)
    @Transactional
    public ArticleDTO createArticle(ArticleDTO dto) {
        // 1. Buscamos el grupo (Foreign Key)
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + dto.getGroupId()));

        // 2. Creamos la entidad
        Article article = new Article();
        article.setName(dto.getName());
        article.setUnitPrice(dto.getUnitPrice());
        article.setStock(dto.getStock());
        article.setGroup(group);

        // 3. Guardamos y retornamos DTO
        Article savedArticle = articleRepository.save(article);
        return convertToDTO(savedArticle);
    }

    // UPDATE (PUT)
    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleDTO dto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));

        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + dto.getGroupId()));

        article.setName(dto.getName());
        article.setUnitPrice(dto.getUnitPrice());
        article.setStock(dto.getStock());
        article.setGroup(group);

        Article updatedArticle = articleRepository.save(article);
        return convertToDTO(updatedArticle);
    }

    // DELETE
    @Transactional
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new RuntimeException("Article not found with id: " + id);
        }
        articleRepository.deleteById(id);
    }

    // Método utilitario PRIVADO para mapear de Entity a DTO
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setName(article.getName());
        dto.setUnitPrice(article.getUnitPrice());
        dto.setStock(article.getStock());
        dto.setGroupId(article.getGroup().getId());
        return dto;
    }
}