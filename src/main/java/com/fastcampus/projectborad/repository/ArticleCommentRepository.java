package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long>, QuerydslPredicateExecutor<ArticleComment>, QuerydslBinderCustomizer<QArticleComment> {

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.including(root.content, root.createdAt, root.createdBy);
        //문자열 표현식::포함 여부(대소문자 구분하지 않음)
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        //날짜/시간 표현식::동일 여부
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

    void deleteByIdAndUserAccount_UserId(Long articleCommentId, String userId);

    List<ArticleComment> findByArticle_Id(Long articleId);

    List<ArticleComment> findByArticleIdOrderByRootCommentId(Long rootCommentId);

}