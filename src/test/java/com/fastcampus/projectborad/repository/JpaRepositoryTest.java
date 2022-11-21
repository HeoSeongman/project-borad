package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.config.JpaConfig;
import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.UserAccount;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository,
                             @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {

//        assertEquals(0, articleRepository.count());
//        System.out.println(articleRepository.count());
//        userAccountRepository.save(UserAccount.of("tjdaks0804", "TJDaks!@06", "tjdaks0804@naver.com", "Duri", "Hi, I'm Duri."));
        UserAccount userAccount = userAccountRepository.findById(1L).get();
//        Article article = articleRepository.save(Article.of("Title 2", userAccount, "Content 2", "#Spring"));
//        System.out.println(article);
        for (int i = 3; i < 100; i++) {
            articleRepository.save(Article.of("Title " + i, userAccount, "Content " + i, "#Spring for"));
        }
        System.out.println(articleRepository.findById(1L));
    }
//
//
//    @DisplayName("insert 테스트")
//    @Test
//    @Order(2)
//    void givenTestData_whenInserting_thenWorksFine() {
//        List<Article> articles = articleRepository.findAll();
//
//        assertEquals(0, articles.size());
//        long previousCount = articleRepository.count();
//
//        Article savedArticle = articleRepository.saveAndFlush(
//                Article.of(
//                        "new article 1",
//                        UserAccount.of("tjdaks0804", "TJDaks!@06", "tjdaks0804@naver.com", "Duri", "뽐나는차두리입니다."),
//                        "new content 11111",
//                        "#spring"));
//
//        assertEquals(previousCount + 1, articleRepository.count());
//        assertNotNull(savedArticle);
//    }
//
//    @DisplayName("update 테스트")
//    @Test
//    @Order(3)
//    void givenTestData_whenUpdating_thenWorksFine() {
//
////        Article savedArticle = articleRepository.save(Article.of("new article 1", "new content 11111", "#spring"));
//        List<Article> all = articleRepository.findAll();
//        System.out.println("length -----------> " + all.size());
//        System.out.println(articleRepository.findAll());
//        Article article = articleRepository.findById(1L).orElseThrow();
//        System.out.println(article);
//        String updatedHashtag = "#springBoot";
//        article.setHashtag(updatedHashtag);
//
//        Article savedArticle = articleRepository.saveAndFlush(article);
//        System.out.println(article);
//
//        assertEquals(updatedHashtag, savedArticle.getHashtag());
//    }
//
//    @DisplayName("delete 테스트")
//    @Test
//    @Order(4)
//    void givenTestData_whenDeleting_thenWorksFine() {
//
////        Article savedArticle = articleRepository.save(Article.of("new article 1", "new content 11111", "#spring"));
//        List<Article> all = articleRepository.findAll();
//        System.out.println("length -----------> " + all.size());
//        Article article = articleRepository.findById(1L).orElseThrow();
//        System.out.println("delete" + article);
//        long previousArticleCount = articleRepository.count();
//        long previousCommentCount = articleCommentRepository.count();
//        long deletedCommnetsSize = article.getArticleComments().size();
//
//        articleRepository.delete(article);
//
//        assertEquals(previousArticleCount - 1 , articleRepository.count());
//        assertEquals(previousCommentCount - deletedCommnetsSize , articleCommentRepository.count());
//    }
}
