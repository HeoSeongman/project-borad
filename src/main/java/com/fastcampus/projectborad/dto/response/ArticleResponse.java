package com.fastcampus.projectborad.dto.response;

import com.fastcampus.projectborad.dto.ArticleDto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.Article} entity
 */
public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String userId,
        String nickname,
        String email
) implements Serializable {
    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String userId, String nickname, String email) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, userId, nickname, email);
    }

    public static ArticleResponse from(ArticleDto articleDto) {
        String nickname = articleDto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = articleDto.userAccountDto().userId();
        }

        return new ArticleResponse(
                articleDto.id(),
                articleDto.title(),
                articleDto.content(),
                articleDto.hashtag(),
                articleDto.createdAt(),
                articleDto.userAccountDto().userId(),
                nickname,
                articleDto.userAccountDto().email()
        );
    }
}