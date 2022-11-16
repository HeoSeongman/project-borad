package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}