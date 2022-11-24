package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.ArticleCommentDto;
import com.fastcampus.projectborad.dto.request.ArticleCommentRequest;
import com.fastcampus.projectborad.repository.ArticleCommentRepository;
import com.fastcampus.projectborad.repository.ArticleRepository;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    public List<ArticleCommentDto> searchArticleComment(Long articleID) {
//        return articleCommentRepository
        return null;
    }

    public void saveArticleComment(ArticleCommentDto articleCommentDto) {

//        UserAccount 엔티티에 public 혹은 protected 기본 생성자를 작성하지 않아서
//        Private constructors don't work with runtime proxies! 비공개 생성자는 런타임 프록시에서 작동하지 않습니다! 오류가 났다.
//        https://velog.io/@ohzzi/JPA의-엔티티에-protected-public-기본-생성자가-필요한-이유 <-(링크) 를 살펴보았다.
//        https://velog.io/@eden/JPA-%EC%97%90%EC%84%9C-getById-vs-findById <-(링크) 참조 형식(프록시 객체)과 실제값 형식(실제 객체)

//        Article article = articleRepository.findById(articleCommentDto.articleId()).orElseThrow();
        Article article = articleRepository.getReferenceById(articleCommentDto.articleId());
        UserAccount userAccount = userAccountRepository.getReferenceById(articleCommentDto.userAccountDto().id());
//        System.out.println("userAccount : " + userAccount.getEmail());
//        UserAccount userAccount = userAccountRepository.findById(articleCommentDto.userAccountDto().id()).orElseThrow();
//        UserAccount userAccount = userAccountRepository.getReferenceById(articleCommentDto.userAccountDto().id());


        ArticleComment articleComment = articleCommentDto.toEntity(article, userAccount);
        articleCommentRepository.save(articleComment);
    }

    public void deleteArticleComment(Long commentId) {
        articleCommentRepository.deleteById(commentId);
    }
}
