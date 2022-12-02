package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.dto.ReplyCommentDto;
import com.fastcampus.projectborad.dto.UserAccountDto;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.ReplyComment} entity
 */
public record ReplyCommentRequest(
        Long articleId,
        Long articleCommentId,
        String content

) implements Serializable {

    public static ReplyCommentRequest of(Long articleId, Long articleCommentId, String content) {
        return new ReplyCommentRequest(articleId, articleCommentId, content);
    }

    public ReplyCommentDto toDto(UserAccountDto userAccountDto) {
        return ReplyCommentDto.of(
                articleCommentId,
                userAccountDto,
                content
        );
    }
}