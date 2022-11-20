package com.fastcampus.projectborad.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.Article} entity
 */
public record ArticleDtoResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String nickname,
        String email
) implements Serializable {
    public static ArticleDtoResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String nickname, String email) {
        return new ArticleDtoResponse(id, title, content, hashtag, createdAt, nickname, email);
    }

    public static ArticleDtoResponse from(ArticleDto articleDto) {
        String nickname = articleDto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = articleDto.userAccountDto().userId();
        }

        return new ArticleDtoResponse(
                articleDto.id(),
                articleDto.title(),
                articleDto.content(),
                articleDto.hashtag(),
                articleDto.createdAt(),
                nickname,
                articleDto.userAccountDto().email()
        );
    }
}