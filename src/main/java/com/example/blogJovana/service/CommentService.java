package com.example.blogJovana.service;

import com.example.blogJovana.request.CommentDetailsRequest;
import com.example.blogJovana.response.CommentDetailsResponse;

import java.util.List;

public interface CommentService {
    CommentDetailsResponse createComment(String jwtEmail, Long postId, CommentDetailsRequest request);
    CommentDetailsResponse updateComment(String jwtEmail, Long commentId, CommentDetailsRequest request);
    void deleteComment(String jwtEmail, Long commentId);
    CommentDetailsResponse getComment(Long commentId);
    List<CommentDetailsResponse> getCommentsByPostId(Long postId);
}
