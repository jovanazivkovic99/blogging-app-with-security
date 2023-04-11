package com.example.blogJovana.controller;

import com.example.blogJovana.request.CommentDetailsRequest;
import com.example.blogJovana.request.CommentUpdateRequest;
import com.example.blogJovana.response.CommentDetailsResponse;
import com.example.blogJovana.service.impl.CommentServiceImpl;
import com.example.blogJovana.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;
    private final JwtServiceImpl jwtService;

    @PostMapping
    public CommentDetailsResponse createComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                @RequestBody @Valid CommentDetailsRequest request) {
        String jwtEmail = jwtService.extractEmail(jwt);
        return commentService.createComment(jwtEmail, request);
    }

    @PutMapping("/{commentId}")
    public CommentDetailsResponse updateComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                @PathVariable Long commentId,
                                                @RequestBody @Valid CommentUpdateRequest request) {
        String jwtEmail = jwtService.extractEmail(jwt);
        return commentService.updateComment(jwtEmail, commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public CommentDetailsResponse deleteComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                @PathVariable Long commentId) {
        String jwtEmail = jwtService.extractEmail(jwt);
        return commentService.deleteComment(jwtEmail, commentId);
    }

    @GetMapping("/{commentId}")
    public CommentDetailsResponse getCommentById(
            @PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/{postId}")
    public List<CommentDetailsResponse> getCommentsByPostId(
            @PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
}
