package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.dto.ArticleDto;
import com.fastcampus.projectborad.dto.UserAccountDto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.Article} entity
 */
public record ArticleRequest(
        String title,
        String content,
        String hashtag

) implements Serializable {

    public ArticleRequest(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static ArticleRequest of(String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                this.title,
                this.content,
                this.hashtag,
                userAccountDto);
    }
}