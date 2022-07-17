package com.example.manutdbackend.controllers;

import com.example.manutdbackend.exception.ResourceNotFoundException;
import com.example.manutdbackend.models.Article;
import com.example.manutdbackend.repository.ArticleRepository;
import com.example.manutdbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        articleRepository.findAll().forEach(articles::add);
        if (articles.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Collections.reverse(articles);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ARTICLE with id = " + id));
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/articles")
    public ResponseEntity<Article> createArticle(@PathVariable("userId") Long userId,@RequestBody Article article) {
        Article _article = userRepository.findById(userId).map(user -> {
            article.setCreatedAt(new Date());
            article.setUpdatedAt(new Date());
            article.setAuthor(user);
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found USER with id = " + userId));
        return new ResponseEntity<>(_article, HttpStatus.CREATED);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") Long id, @RequestBody Article article) {
        Article _article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ARTICLE with id = " + id));
        _article.setTitle(article.getTitle());
        _article.setDescription(article.getDescription());
        _article.setImage(article.getImage());
        _article.setContent(article.getContent());
        _article.setUpdatedAt(new Date());
        return new ResponseEntity<>(articleRepository.save(_article), HttpStatus.OK);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable("id") Long id) {
        articleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
