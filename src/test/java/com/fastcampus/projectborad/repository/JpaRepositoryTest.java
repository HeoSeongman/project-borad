package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.config.JpaConfig;
import com.fastcampus.projectborad.domain.Article;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
@Rollback(value = false)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    @Order(1)
    void givenTestData_whenSelecting_thenWorksFine() {

//        assertEquals(0, articleRepository.count());
        System.out.println(articleRepository.count());
    }

    @DisplayName("insert 테스트")
    @Test
    @Order(2)
    void givenTestData_whenInserting_thenWorksFine() {
//        List<Article> articles = articleRepository.findAll();

//        assertEquals(0, articles.size());
        long previousCount = articleRepository.count();

        Article savedArticle = articleRepository.saveAndFlush(Article.of("new article 1", "new content 11111", "#spring"));

        assertEquals(previousCount + 1, articleRepository.count());
        assertNotNull(savedArticle);
    }

    @DisplayName("update 테스트")
    @Test
    @Order(3)
    void givenTestData_whenUpdating_thenWorksFine() {

//        Article savedArticle = articleRepository.save(Article.of("new article 1", "new content 11111", "#spring"));
        List<Article> all = articleRepository.findAll();
        System.out.println("length -----------> " + all.size());
        System.out.println(articleRepository.findAll());
        Article article = articleRepository.findById(1L).orElseThrow();
        System.out.println(article);
        String updatedHashtag = "#springBoot";
        article.setHashtag(updatedHashtag);

        Article savedArticle = articleRepository.saveAndFlush(article);
        System.out.println(article);

        assertEquals(updatedHashtag, savedArticle.getHashtag());
    }

    @DisplayName("delete 테스트")
    @Test
    @Order(4)
    void givenTestData_whenDeleting_thenWorksFine() {

//        Article savedArticle = articleRepository.save(Article.of("new article 1", "new content 11111", "#spring"));
        List<Article> all = articleRepository.findAll();
        System.out.println("length -----------> " + all.size());
        Article article = articleRepository.findById(1L).orElseThrow();
        System.out.println("delete" + article);
        long previousArticleCount = articleRepository.count();
        long previousCommentCount = articleCommentRepository.count();
        long deletedCommnetsSize = article.getArticleComments().size();

        articleRepository.delete(article);

        assertEquals(previousArticleCount - 1 , articleRepository.count());
        assertEquals(previousCommentCount - deletedCommnetsSize , articleCommentRepository.count());
    }
}
