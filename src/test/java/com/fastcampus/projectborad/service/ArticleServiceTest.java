package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.config.JpaConfig;
import com.fastcampus.projectborad.domain.Article;
import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.ArticleDto;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.repository.ArticleRepository;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("게시글 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    //ArticleService 는 ArticleRepository 가 필요하다.
    //@InjectMocks 로 선언하면 @Mock 으로 선언된 ArticleRepository 객체를  ArticleService 객체에 주입해준다.

    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserAccountRepository userAccountRepository;
    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환")
    @Test
    void test01() {
        Page<ArticleDto> articleDtos = articleService.searchArticles(SearchType.Title, "search keyword");

        assertNotNull(articleDtos);
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환")
    @Test
    void test02() {
        ArticleDto articleDto = articleService.searchArticle(1L);

        assertNotNull(articleDto);
    }

    @DisplayName("게시글을 쓰면, 게시글 생성")
    @Test
    void test03() {
//        ArticleRequest articleDto = ArticleRequest.of("게시글 생성", "게시글을 쓰면, 게시글을 생성함", "Spring");
////        articleRepository.save 가 호출되고
////        Article 클래스를 가진 어떠한(any) 객체가
////        주어졌을 때(given)
////        null 이 반환되는지? 를 조건으로 지정
//        given(articleRepository.save(any(Article.class))).willReturn(null);
//
//        articleService.saveArticle(articleDto);
//        //그렇다면(then) articleRepository mock 객체가
//        //Article 클래스를 가진 어떠한(any) 객체를
//        //save 할 수 있는지?(should) 검증
//        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 id와 수정된 정보를 입력하면, 게시글 수정")
    @Test
    void test04() {
//        ArticleRequest articleDto = ArticleRequest.of("게시글 수정", "게시글을 쓰면, 게시글을 수정함", "#Spring");
//        given(articleRepository.save(any(Article.class))).willReturn(null);
//
////        articleService.updateArticle(articleDto);
//
//        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 id를 입력하면, 게시글 삭제")
    @Test
    void test05() {
        //willDoNothing, doNothing -> 검증하고 싶은 메소드가 void 일 때 조건 지정한다.
        //articleRepository 가 mock 객체로 주어졌을 때(given)
        //Article 클래스를 가진 어떠한(any) 객체가
        //삭제될 때(delete) 를 조건으로 지정
        willDoNothing().given(articleRepository).delete(any(Article.class));

//        articleService.deleteArticle(1L);

        //그렇다면(then) articleRepository mock 객체가
        //Article 클래스를 가진 어떠한(any) 객체를
        //delete 할 수 있는지?(should) 검증
        then(articleRepository).should().delete(any(Article.class));
    }


}