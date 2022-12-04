package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.dto.ArticleCommentDto;
import com.fastcampus.projectborad.dto.ReplyCommentDto;
import com.fastcampus.projectborad.dto.request.ArticleCommentRequest;
import com.fastcampus.projectborad.dto.request.ArticleCommentResponse;
import com.fastcampus.projectborad.dto.request.ReplyCommentRequest;
import com.fastcampus.projectborad.dto.request.ReplyCommentResponse;
import com.fastcampus.projectborad.dto.security.BoardPrincipal;
import com.fastcampus.projectborad.service.ArticleCommentService;
import com.fastcampus.projectborad.service.ReplyCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;
    private final ReplyCommentService replyCommentService;

    @PostMapping("/create")
    public String createComment(ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable Long commentId , ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
//        articleCommentService.updateComment(commentId, replyCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + replyCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Long articleId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/replyCreate")
    public String createReplyComment(ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        replyCommentService.saveReplyComment(replyCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + replyCommentRequest.articleId();
    }

    @PostMapping("/{replyId}/replyUpdate")
    public String updateReplyComment(@PathVariable Long replyId, ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("답글 수정 : " + replyCommentRequest.toString());

        replyCommentService.updateReplyComment(replyId, replyCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + replyCommentRequest.articleId();
    }

    @PostMapping("/replyDelete")
    public String deleteReplyComment(Long replyCommentId, Long articleId) {
        System.out.println("replyCommentId : " + replyCommentId);

//        replyCommentService.deleteReplyComment(replyCommentId);

        return "redirect:/articles/" + articleId;
    }



    // 아래는 비동기 매핑

    @ResponseBody
    @PostMapping("/createCommentJSON")
    public ResponseEntity<ArticleCommentResponse> createCommentJSON(@RequestBody ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/createCommentJSON JSON 수신 : " + articleCommentRequest.toString());

        ArticleCommentDto savedArticleCommentDto = articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));
        ArticleCommentResponse articleCommentResponse = ArticleCommentResponse.from(savedArticleCommentDto);

        return ResponseEntity.ok(articleCommentResponse);
    }

    @ResponseBody
    @PostMapping("/{commentId}/updateCommentJSON")
    public ResponseEntity<ArticleCommentRequest> updateCommentJSON(@PathVariable Long commentId, @RequestBody ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/updateCommentJSON JSON 수신 : " + articleCommentRequest.toString());

        articleCommentService.updateComment(commentId, articleCommentRequest.toDto(boardPrincipal.toDto()));

        return ResponseEntity.ok(articleCommentRequest);
    }
    @ResponseBody
    @PostMapping("/deleteCommentJSON")
    public ResponseEntity<Boolean> deleteCommentJSON(@RequestBody Long commentId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/deleteCommentJSON JSON 수신 : " + commentId);

        articleCommentService.deleteArticleComment(commentId);

        return ResponseEntity.ok(Boolean.TRUE);
    }

    @ResponseBody
    @PostMapping("/createReplyJSON")
    public ResponseEntity<ReplyCommentResponse> createReplyCommentJSON(@RequestBody ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/replyCreateJSON JSON 수신 : " + replyCommentRequest.toString());

        ReplyCommentResponse replyCommentResponse = ReplyCommentResponse.from(replyCommentService.saveReplyComment(replyCommentRequest.toDto(boardPrincipal.toDto())));

        return ResponseEntity.ok(replyCommentResponse);
    }

    @ResponseBody
    @PostMapping("/{replyId}/updateReplyJSON")
    public ResponseEntity<ReplyCommentRequest> updateReplyCommentJSON(@PathVariable Long replyId,@RequestBody ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/replyUpdateJSON JSON 수신 : " + replyCommentRequest.toString() + ", Id : " + replyId);

        replyCommentService.updateReplyComment(replyId, replyCommentRequest.toDto(boardPrincipal.toDto()));

        return ResponseEntity.ok(replyCommentRequest);
    }

    @ResponseBody
    @PostMapping("/deleteReplyJSON")
    public ResponseEntity<Boolean> deleteReplyCommentJSON(@RequestBody Long replyId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/deleteCommentJSON JSON 수신 : " + replyId);

        replyCommentService.deleteReplyComment(replyId);

        return ResponseEntity.ok(Boolean.TRUE);
    }


}
