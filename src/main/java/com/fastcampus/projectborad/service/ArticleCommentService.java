package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.dto.ArticleCommentDto;
import com.fastcampus.projectborad.repository.ArticleCommentRepository;
import com.fastcampus.projectborad.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    public List<ArticleCommentDto> searchArticleComment(Long articleID) {
        return List.of();
    }
}
