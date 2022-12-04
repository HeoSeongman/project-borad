package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.dto.ArticleCommentDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link ArticleComment} entity
 */
public record ArticleCommentResponse(
        Long id,
        String content,
        boolean isDeleted,
        Set<ReplyCommentResponse> replyComments,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy

) implements Serializable {

    public static ArticleCommentResponse of(Long id, String content, boolean isDeleted, Set<ReplyCommentResponse> replyComments, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentResponse(id, content, isDeleted, replyComments, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentResponse from(ArticleCommentDto articleCommentDto) {
        return new ArticleCommentResponse(
                articleCommentDto.id(),
                articleCommentDto.content(),
                articleCommentDto.isDeleted(),
                articleCommentDto.replyCommentDtos().stream()
                        .map(ReplyCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                articleCommentDto.createdAt(),
                articleCommentDto.createdBy(),
                articleCommentDto.modifiedAt(),
                articleCommentDto.modifiedBy()
        );
    }

    public ArticleComment toEntity(Article article) {
        return ArticleComment.of(
                article,
                article.getUserAccount(),
                content,
                isDeleted
        );
    }
}