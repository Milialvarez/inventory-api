package com.inventoryapi.repository;

import com.inventoryapi.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.stock < :stockLimit")
    List<Article> findArticlesWithLessStockThan(@Param("stock") Integer stockLimit);

    @Query("SELECT m.article " +
            "FROM Movement m " +
            "JOIN m.article.group g " +
            "WHERE g.id = :groupId " +
            "GROUP BY m.article " +
            "HAVING COUNT(m) >= :minMovements")
    List<Article> findArticlesByGroupWithMinMovements(
            @Param("groupId") Integer groupId,
            @Param("minMovements") Long minMovements
    );
}