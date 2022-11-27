package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.dto.ArticleDto;
import com.fastcampus.projectborad.dto.UserAccountDto;

import java.io.Serializable;

/**
 * A DTO for the {@link Article} entity
 */
public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag

) implements Serializable {

    public static ArticleResponse of(Long id, String title, String content, String hashtag) {
        return new ArticleResponse(id, title, content, hashtag);
    }

    public static ArticleResponse from(ArticleDto articleDto) {
        return ArticleResponse.of(
                articleDto.id(),
                articleDto.title(),
                articleDto.content(),
                articleDto.hashtag()
        );
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                this.title,
                this.content,
                this.hashtag,
                userAccountDto);
    }
}