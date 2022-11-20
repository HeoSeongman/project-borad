package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long>, QuerydslPredicateExecutor<Article>, QuerydslBinderCustomizer<QArticle> {
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        //명시적으로 미등록된 속성을 제외할지 여부, 기본값은 false
        bindings.excludeUnlistedProperties(true);
        //포함시킬 속성들(경로)
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
//        bindings.bind(root.title).first(((path, value) -> path.eq(value)));
        //문자열 표현식::포함 여부(대소문자 구분하지 않음)
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        //날짜/시간 표현식::동일 여부
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByIdContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String title, Pageable pageable);
    Page<Article> findByHashtagContaining(String title, Pageable pageable);
}