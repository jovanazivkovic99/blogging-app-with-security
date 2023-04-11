package com.example.blogJovana.service;

import com.example.blogJovana.request.CommentDetailsRequest;
import com.example.blogJovana.request.CommentUpdateRequest;
import com.example.blogJovana.response.CommentDetailsResponse;

import java.util.List;

public interface CommentService {
    CommentDetailsResponse createComment(String jwtEmail, CommentDetailsRequest request);
    CommentDetailsResponse updateComment(String jwtEmail, Long commentId, CommentUpdateRequest request);
    CommentDetailsResponse deleteComment(String jwtEmail, Long commentId);
    CommentDetailsResponse getCommentById(Long commentId);
    List<CommentDetailsResponse> getCommentsByPostId(Long postId);
}
