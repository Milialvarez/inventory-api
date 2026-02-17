package com.inventoryapi.service;

import com.inventoryapi.dto.ArticleDTO;
import com.inventoryapi.entity.Article;
import com.inventoryapi.entity.Group;
import com.inventoryapi.exception.ResourceNotFoundException;
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

    @Transactional(readOnly = true)
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));
        return convertToDTO(article);
    }

    @Transactional
    public ArticleDTO createArticle(ArticleDTO dto) {
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + dto.getGroupId()));

        Article article = new Article();
        article.setName(dto.getName());
        article.setUnitPrice(dto.getUnitPrice());
        article.setStock(dto.getStock());
        article.setGroup(group);

        Article savedArticle = articleRepository.save(article);
        return convertToDTO(savedArticle);
    }

    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleDTO dto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + dto.getGroupId()));

        article.setName(dto.getName());
        article.setUnitPrice(dto.getUnitPrice());
        article.setStock(dto.getStock());
        article.setGroup(group);

        Article updatedArticle = articleRepository.save(article);
        return convertToDTO(updatedArticle);
    }

    @Transactional
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article not found with id: " + id);
        }
        articleRepository.deleteById(id);
    }

    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setName(article.getName());
        dto.setUnitPrice(article.getUnitPrice());
        dto.setStock(article.getStock());
        dto.setGroupId(article.getGroup().getId());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ArticleDTO> findArticlesWithLessStockThan(Integer stock) {
        List<Article> articles = articleRepository.findArticlesWithLessStockThan(stock);

        if (articles.isEmpty()) {
            throw new ResourceNotFoundException("No articles found with stock less than: " + stock);
        }

        return articles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}