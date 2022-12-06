package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.ArticleCommentDto;
import com.fastcampus.projectborad.dto.ReplyCommentDto;
import com.fastcampus.projectborad.dto.request.ArticleCommentRequest;
import com.fastcampus.projectborad.dto.response.ArticleCommentResponse;
import com.fastcampus.projectborad.repository.ArticleCommentRepository;
import com.fastcampus.projectborad.repository.ArticleRepository;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    public Set<ArticleCommentResponse> searchArticleComment(Long articleID) {

        return articleCommentRepository.findByArticleIdOrderByRootCommentId(articleID).stream()
                .map(ArticleCommentDto::from)
                .map(ArticleCommentResponse::from)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ArticleCommentDto saveArticleComment(ArticleCommentDto articleCommentDto, Long parentCommentId) {

//        UserAccount 엔티티에 public 혹은 protected 기본 생성자를 작성하지 않아서
//        Private constructors don't work with runtime proxies! 비공개 생성자는 런타임 프록시에서 작동하지 않습니다! 오류가 났다.
//        https://velog.io/@ohzzi/JPA의-엔티티에-protected-public-기본-생성자가-필요한-이유 <-(링크) 를 살펴보았다.
//        https://velog.io/@eden/JPA-%EC%97%90%EC%84%9C-getById-vs-findById <-(링크) 참조 형식(프록시 객체)과 실제값 형식(실제 객체)

//        Article article = articleRepository.findById(articleCommentDto.articleId()).orElseThrow();
        Article article = articleRepository.getReferenceById(articleCommentDto.articleId());
        UserAccount userAccount = userAccountRepository.getReferenceById(articleCommentDto.userAccountDto().userId());
//        System.out.println("userAccount : " + userAccount.getEmail());
//        UserAccount userAccount = userAccountRepository.findById(articleCommentDto.userAccountDto().id()).orElseThrow();
//        UserAccount userAccount = userAccountRepository.getReferenceById(articleCommentDto.userAccountDto().id());

        ArticleComment savedArticleComment = articleCommentRepository.save(articleCommentDto.toEntity(article, userAccount));

        if (parentCommentId == 0) {
            savedArticleComment.setRootCommentId(savedArticleComment.getId());
        } else {
            ArticleComment parentArticleComment = articleCommentRepository.findById(parentCommentId).orElseThrow();
            savedArticleComment.setRootCommentId(parentArticleComment.getRootCommentId());
        }

        savedArticleComment.setParentCommentId(parentCommentId);

        return ArticleCommentDto.from(savedArticleComment);
    }

    public void updateComment(Long commentId, ArticleCommentDto articleCommentDto) {
        ArticleComment comment = articleCommentRepository.getReferenceById(commentId);

        if (articleCommentDto.content() != null) {
            comment.setContent(articleCommentDto.content());
        }
    }

    public void deleteArticleComment(Long commentId) {
        articleCommentRepository.findById(commentId);
        ArticleComment articleComment = articleCommentRepository.findById(commentId).orElseThrow();
        articleComment.setDeleted(true);

        articleCommentRepository.save(articleComment);
    }
}
