package com.example.manutdbackend.repository;

import com.example.manutdbackend.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthorId(Long authorId);
}
