package com.fastcampus.projectborad.dto;

import com.fastcampus.projectborad.domain.Article;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleAndCommentsDtoResponse(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        Set<ArticleCommentDtoResponse> articleCommentsDtoResponse,
        LocalDateTime createdAt
) {

    public static ArticleAndCommentsDtoResponse of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, Set<ArticleCommentDtoResponse> articleCommentDtos, LocalDateTime createdAt) {
        return new ArticleAndCommentsDtoResponse(id, userAccountDto, title, content, hashtag, articleCommentDtos, createdAt);
    }

    public static ArticleAndCommentsDtoResponse from(ArticleAndCommentsDto articleAndCommentsDto) {
        String nickname = articleAndCommentsDto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = articleAndCommentsDto.userAccountDto().userId();
        }

        return new ArticleAndCommentsDtoResponse(
                articleAndCommentsDto.id(),
                articleAndCommentsDto.userAccountDto(),
                articleAndCommentsDto.title(),
                articleAndCommentsDto.content(),
                articleAndCommentsDto.hashtag(),
                articleAndCommentsDto.articleCommentDtos().stream()
                        .map(ArticleCommentDtoResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                articleAndCommentsDto.createdAt()
        );
    }

}
