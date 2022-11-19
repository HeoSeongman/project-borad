package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.ArticleDto;
import com.fastcampus.projectborad.dto.ArticleUpdateDto;
import com.fastcampus.projectborad.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

//    @Transactional(readOnly = true)
//    public List<ArticleDto> searchArticles(SearchType title, String search_keyword) {
//        return List.of();
//    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDto articleDto) {

    }

    public void updateArticle(long id, ArticleUpdateDto articleDto) {

    }

    public void deleteArticle(long id) {

    }
}
