package com.fastcampus.projectborad.dto;

import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.domain.ReplyComment;
import com.fastcampus.projectborad.domain.UserAccount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.ReplyComment} entity
 */
public record ReplyCommentDto(
        Long id,
        Long articleCommentId,
        UserAccountDto userAccount,
        String content,
        boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy

) implements Serializable {

    public static ReplyCommentDto of(Long id, Long articleCommentId, UserAccountDto userAccountDto, String content, boolean isDeleted, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ReplyCommentDto(id, articleCommentId, userAccountDto, content, isDeleted, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ReplyCommentDto of(Long articleCommentId, UserAccountDto userAccountDto, String content) {
        return new ReplyCommentDto(null, articleCommentId, userAccountDto, content, false, null, null, null, null);
    }

    public static ReplyCommentDto from(ReplyComment replyComment) {
        return ReplyCommentDto.of(
                replyComment.getId(),
                replyComment.getArticleComment().getId(),
                UserAccountDto.from(replyComment.getUserAccount()),
                replyComment.getContent(),
                replyComment.isDeleted(),
                replyComment.getCreatedAt(),
                replyComment.getCreatedBy(),
                replyComment.getModifiedAt(),
                replyComment.getModifiedBy()
        );
    }

    public ReplyComment toEntity(ArticleComment articleComment, UserAccount userAccount) {
        return ReplyComment.of(
                articleComment,
                userAccount,
                content
        );
    }
}