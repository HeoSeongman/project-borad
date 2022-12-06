package com.fastcampus.projectborad.dto;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.domain.UserAccount;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.Article} entity
 */
public record ArticleDto(
        LocalDateTime createdAt,
        String createdBy,
        Long id,
        String title,
        UserAccountDto userAccountDto,
        String content,
        String hashtag

) implements Serializable {

    public static ArticleDto of(LocalDateTime createdAt, String createdBy, Long id, String title, UserAccountDto userAccountDto, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, id, title, userAccountDto, content, hashtag);
    }

    public static ArticleDto of(String title, String content, String hashtag, UserAccountDto userAccountDto) {
        return new ArticleDto(null, null, null,  title, userAccountDto, content, hashtag);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getId(),
                entity.getTitle(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getHashtag()
        );
    }

    public Article toEntity() {
        return Article.of(
                title,
                userAccountDto.toEntity(),
                content,
                hashtag
        );
    }
}