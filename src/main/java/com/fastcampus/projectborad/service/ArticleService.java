package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.ArticleAndCommentsDto;
import com.fastcampus.projectborad.dto.ArticleDto;
import com.fastcampus.projectborad.dto.ArticleUpdateDto;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.repository.ArticleCommentRepository;
import com.fastcampus.projectborad.repository.ArticleRepository;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String search_keyword, Pageable pageable) {
        if (search_keyword == null || search_keyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case Title -> articleRepository.findByTitleContaining(search_keyword, pageable).map(ArticleDto::from);
            case Content -> articleRepository.findByContentContaining(search_keyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(search_keyword, pageable).map(ArticleDto::from);
            case NickName -> articleRepository.findByUserAccount_NicknameContaining(search_keyword, pageable).map(ArticleDto::from);
            case Hashtag -> articleRepository.findByHashtagContaining(search_keyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow( () -> new EntityNotFoundException("게시글이 없습니다. - articleId " + articleId));

    }

    @Transactional(readOnly = true)
    public ArticleAndCommentsDto searchArticleAndCommentDto(Long articleId) {
        return ArticleAndCommentsDto.from(
                articleRepository.findById(articleId).get(),
                articleCommentRepository.findByArticleIdOrderByRootCommentId(articleId));
    }

    public Long saveArticle(ArticleDto articleDto) {

        Article article = articleRepository.save(articleDto.toEntity());
        return article.getId();
    }

    public void updateArticle(Long articleId, ArticleDto articleDto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(articleDto.userAccountDto().userId());

            if (article.getUserAccount().equals(userAccount)) {
                if (articleDto.title() != null) { article.setTitle(articleDto.title()); }
                if (articleDto.content() != null) { article.setContent(articleDto.content()); }
                article.setHashtag(articleDto.hashtag());
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다." + e.getLocalizedMessage());
        }
    }

    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }
}
