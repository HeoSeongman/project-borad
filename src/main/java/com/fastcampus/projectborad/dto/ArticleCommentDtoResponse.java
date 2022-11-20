package com.fastcampus.projectborad.dto;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.ArticleComment;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ArticleComment} entity
 */
public record ArticleCommentDtoResponse(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content

) implements Serializable {

    public static ArticleCommentDtoResponse of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String content) {
        return new ArticleCommentDtoResponse(createdAt, createdBy, modifiedAt, modifiedBy, content);
    }

    public static ArticleCommentDtoResponse from(ArticleCommentDto articleCommentDto) {
        return new ArticleCommentDtoResponse(
                articleCommentDto.createdAt(),
                articleCommentDto.createdBy(),
                articleCommentDto.modifiedAt(),
                articleCommentDto.modifiedBy(),
                articleCommentDto.content()
        );
    }

    public ArticleComment toEntity(Article article) {
        return ArticleComment.of(
                article,
                content
        );
    }
}