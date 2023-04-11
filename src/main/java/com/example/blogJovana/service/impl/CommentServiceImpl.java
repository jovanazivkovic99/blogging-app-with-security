package com.example.blogJovana.service.impl;

import com.example.blogJovana.exceptions.*;
import com.example.blogJovana.mapper.CommentMapper;
import com.example.blogJovana.model.Category;
import com.example.blogJovana.model.Comment;
import com.example.blogJovana.model.Post;
import com.example.blogJovana.model.User;
import com.example.blogJovana.repository.CommentRepository;
import com.example.blogJovana.repository.PostRepository;
import com.example.blogJovana.repository.UserRepository;
import com.example.blogJovana.request.CommentDetailsRequest;
import com.example.blogJovana.request.CommentUpdateRequest;
import com.example.blogJovana.response.CommentDetailsResponse;
import com.example.blogJovana.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDetailsResponse createComment(String jwtEmail, CommentDetailsRequest request) {
        User user = userRepository.findByEmail(jwtEmail)
                .orElseThrow(() -> new UserNotFoundException(jwtEmail));
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new PostNotFoundException(request.postId()));

        Comment comment = commentMapper.toComment(request);
        comment.setUser(user);
        comment.setPost(post);
        //comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentDetailsResponse(savedComment);
    }

    @Override
    public CommentDetailsResponse updateComment(String jwtEmail, Long commentId, CommentUpdateRequest request) {
        User user = userRepository.findByEmail(jwtEmail)
                .orElseThrow(() -> new UserNotFoundException(jwtEmail));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getUser().equals(user)) {
            throw new UnauthorizedActionException(jwtEmail);
        }
        /*Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new PostNotFoundException(request.postId()));*/
        comment.setTitle(request.title());
        comment.setComment(request.comment());
        //comment.setPost(post);
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toCommentDetailsResponse(updatedComment);
    }

    @Override
    public CommentDetailsResponse deleteComment(String jwtEmail, Long commentId) {
        User user = userRepository.findByEmail(jwtEmail)
                .orElseThrow(() -> new UserNotFoundException(jwtEmail));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getUser().equals(user)) {
            throw new UnauthorizedActionException(jwtEmail);
        }
        commentRepository.delete(comment);
        return commentMapper.toCommentDetailsResponse(comment);
    }

    @Override
    public CommentDetailsResponse getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        return commentMapper.toCommentDetailsResponse(comment);
    }

    @Override
    public List<CommentDetailsResponse> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return commentMapper.toCommentDetailsList(comments);
    }
}
