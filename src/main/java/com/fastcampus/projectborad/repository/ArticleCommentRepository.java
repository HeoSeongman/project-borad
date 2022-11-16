package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}