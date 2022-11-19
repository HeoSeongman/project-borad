package com.fastcampus.projectborad.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.Article} entity
 */
public record ArticleUpdateDto(String title, String content, String hashtag) implements Serializable {

    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }
}