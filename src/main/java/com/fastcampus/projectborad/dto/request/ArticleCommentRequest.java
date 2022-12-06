package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.dto.ArticleCommentDto;
import com.fastcampus.projectborad.dto.UserAccountDto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.ArticleComment} entity
 */
public record ArticleCommentRequest(
        Long articleId,
        String content,
        Long parentCommentId,
        Long commentId

) implements Serializable {

    public static ArticleCommentRequest of(Long articleId, String content, Long parentCommentId, Long commentId) {
        return new ArticleCommentRequest(articleId, content, parentCommentId, commentId);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}