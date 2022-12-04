package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.dto.ReplyCommentDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.ReplyComment} entity
 */
public record ReplyCommentResponse(
        Long id,
        String content,
        boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy

) implements Serializable {

    public static ReplyCommentResponse of(Long id, String content, boolean isDeleted, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ReplyCommentResponse(id, content, isDeleted, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ReplyCommentResponse from(ReplyCommentDto replyCommentDto) {
        return ReplyCommentResponse.of(
                replyCommentDto.id(),
                replyCommentDto.content(),
                replyCommentDto.isDeleted(),
                replyCommentDto.createdAt(),
                replyCommentDto.createdBy(),
                replyCommentDto.modifiedAt(),
                replyCommentDto.modifiedBy()
        );
    }
}