package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.dto.ArticleCommentDto;
import com.fastcampus.projectborad.dto.request.ArticleCommentRequest;
import com.fastcampus.projectborad.dto.response.ArticleCommentResponse;
import com.fastcampus.projectborad.dto.request.ReplyCommentRequest;
import com.fastcampus.projectborad.dto.response.ReplyCommentResponse;
import com.fastcampus.projectborad.dto.security.BoardPrincipal;
import com.fastcampus.projectborad.service.ArticleCommentService;
import com.fastcampus.projectborad.service.ReplyCommentService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;
    private final ReplyCommentService replyCommentService;

    @PostMapping("/create")
    public String createComment(ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

//        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

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
        System.out.println("?????? ?????? : " + replyCommentRequest.toString());

        replyCommentService.updateReplyComment(replyId, replyCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + replyCommentRequest.articleId();
    }

    @PostMapping("/replyDelete")
    public String deleteReplyComment(Long replyCommentId, Long articleId) {
        System.out.println("replyCommentId : " + replyCommentId);

//        replyCommentService.deleteReplyComment(replyCommentId);

        return "redirect:/articles/" + articleId;
    }



    // ????????? ????????? ??????

    @ResponseBody
    @PostMapping("/createComment")
    public ResponseEntity<ArticleCommentResponse> createCommentJSON(@RequestBody ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/createCommentJSON JSON ?????? : " + articleCommentRequest.toString());

        ArticleCommentDto savedArticleCommentDto = articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()), articleCommentRequest.parentCommentId());
        ArticleCommentResponse articleCommentResponse = ArticleCommentResponse.from(savedArticleCommentDto);

        return ResponseEntity.ok(articleCommentResponse);
    }

    @ResponseBody
    @PostMapping("/updateComment")
    public ResponseEntity<ArticleCommentRequest> updateCommentJSON(@RequestBody ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/updateCommentJSON JSON ?????? : " + articleCommentRequest.toString());

        articleCommentService.updateComment(articleCommentRequest.commentId(), articleCommentRequest.toDto(boardPrincipal.toDto()));

        return ResponseEntity.ok(articleCommentRequest);
    }

    @ResponseBody
    @PostMapping("/deleteComment")
    public ResponseEntity<Boolean> deleteCommentJSON(@RequestBody Long commentId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/deleteCommentJSON JSON ?????? : " + commentId);

        articleCommentService.deleteArticleComment(commentId);

        return ResponseEntity.ok(Boolean.TRUE);
    }





    // ?????? ??????
    @ResponseBody
    @PostMapping("/createReplyJSON")
    public ResponseEntity<ReplyCommentResponse> createReplyCommentJSON(@RequestBody ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/replyCreateJSON JSON ?????? : " + replyCommentRequest.toString());

        ReplyCommentResponse replyCommentResponse = ReplyCommentResponse.from(replyCommentService.saveReplyComment(replyCommentRequest.toDto(boardPrincipal.toDto())));

        return ResponseEntity.ok(replyCommentResponse);
    }

    @ResponseBody
    @PostMapping("/{replyId}/updateReplyJSON")
    public ResponseEntity<ReplyCommentRequest> updateReplyCommentJSON(@PathVariable Long replyId,@RequestBody ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/replyUpdateJSON JSON ?????? : " + replyCommentRequest.toString() + ", Id : " + replyId);

        replyCommentService.updateReplyComment(replyId, replyCommentRequest.toDto(boardPrincipal.toDto()));

        return ResponseEntity.ok(replyCommentRequest);
    }

    @ResponseBody
    @PostMapping("/deleteReplyJSON")
    public ResponseEntity<Boolean> deleteReplyCommentJSON(@RequestBody Long replyId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        System.out.println("/deleteCommentJSON JSON ?????? : " + replyId);

        replyCommentService.deleteReplyComment(replyId);

        return ResponseEntity.ok(Boolean.TRUE);
    }


}
