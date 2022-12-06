package com.fastcampus.projectborad.dto.response;

import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.dto.ArticleCommentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ArticleComment} entity
 */
public record ArticleCommentResponse(
        Long id,
        String nickname,
        String content,
        boolean isDeleted,
        Long rootCommentId,
        Long parentCommentId,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy

) implements Serializable {

    public static ArticleCommentResponse of(Long id, String nickname, String content, boolean isDeleted, Long rootCommentId, Long parentCommentId, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentResponse(id, nickname, content, isDeleted, rootCommentId, parentCommentId, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentResponse from(ArticleCommentDto articleCommentDto) {
        String nickname = articleCommentDto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = articleCommentDto.userAccountDto().userId();
        }

        return new ArticleCommentResponse(
                articleCommentDto.id(),
                nickname,
                articleCommentDto.content(),
                articleCommentDto.isDeleted(),
                articleCommentDto.rootCommentId(),
                articleCommentDto.parentCommentId(),
                articleCommentDto.createdAt(),
                articleCommentDto.createdBy(),
                articleCommentDto.modifiedAt(),
                articleCommentDto.modifiedBy()
        );
    }

}