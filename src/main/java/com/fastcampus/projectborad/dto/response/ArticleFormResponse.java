package com.fastcampus.projectborad.dto.response;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.dto.ArticleDto;
import com.fastcampus.projectborad.dto.UserAccountDto;

import java.io.Serializable;

/**
 * A DTO for the {@link Article} entity
 */
public record ArticleFormResponse(
        Long id,
        String title,
        String content,
        String hashtag

) implements Serializable {

    public static ArticleFormResponse of(Long id, String title, String content, String hashtag) {
        return new ArticleFormResponse(id, title, content, hashtag);
    }

    public static ArticleFormResponse from(ArticleDto articleDto) {
        return ArticleFormResponse.of(
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