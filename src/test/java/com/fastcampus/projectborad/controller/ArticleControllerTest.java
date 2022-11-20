package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.dto.ArticleAndCommentsDto;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 테스트 - 게시글")
// WebMvcTest 의 값이 비어있으면 모든 컨트롤러를 불러들여 테스트를 진행한다. 값을 추가하면 해당 컨트롤러만 테스트한다.
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    public ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("게시글 리스트 정상 호출")
    @Test
    public void test01() throws Exception {

        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));
                // model 에 articles 라는 속성이 있는지?

        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
    }

    @DisplayName("게시글 한 건 정상 호출")
    @Test
    public void test02() throws Exception {
        Long articleID = 1L;
//        given(articleService.searchArticle(articleID)).willReturn(createArticleAndCommentsDto());

        mockMvc.perform(get("/articles/" + articleID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));
                // model 에 articles 라는 속성이 있는지?

        then(articleService).should().searchArticle(articleID);
    }

    @DisplayName("게시글 검색 정상 호출")
    @Test
    public void test03() throws Exception {
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @DisplayName("게시글 해시태그 검색 정상 호출")
    @Test
    public void test04() throws Exception {
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }


    public ArticleAndCommentsDto createArticleAndCommentsDto() {
        return ArticleAndCommentsDto.of(
                1L,
                createUserAccountDto(),
                "title",
                "content",
                "#spring",
                Set.of(),
                LocalDateTime.now(),
                "Heo",
                LocalDateTime.now(),
                "Heo"
        );
    }

    public UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "tjdaks0804",
                "password",
                "HeoNickName",
                "Heo@gmail.com",
                "Heo's Introduce",
                LocalDateTime.now(),
                "Heo",
                LocalDateTime.now(),
                "Heo"
        );
    }
}