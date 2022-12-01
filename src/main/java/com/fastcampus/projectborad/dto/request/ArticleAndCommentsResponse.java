package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.dto.ArticleAndCommentsDto;
import com.fastcampus.projectborad.dto.UserAccountDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleAndCommentsResponse(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        Set<ArticleCommentResponse> articleCommentsDtoResponse,
        LocalDateTime createdAt
) {

    public static ArticleAndCommentsResponse of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, Set<ArticleCommentResponse> articleCommentDtos, LocalDateTime createdAt) {
        return new ArticleAndCommentsResponse(id, userAccountDto, title, content, hashtag, articleCommentDtos, createdAt);
    }

    public static ArticleAndCommentsResponse from(ArticleAndCommentsDto articleAndCommentsDto) {
        String nickname = articleAndCommentsDto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = articleAndCommentsDto.userAccountDto().userId();
        }

        return new ArticleAndCommentsResponse(
                articleAndCommentsDto.id(),
                articleAndCommentsDto.userAccountDto(),
                articleAndCommentsDto.title(),
                articleAndCommentsDto.content(),
                articleAndCommentsDto.hashtag(),
                articleAndCommentsDto.articleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                articleAndCommentsDto.createdAt()
        );
    }

}
