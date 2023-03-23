package com.example.blogJovana.service;

import com.example.blogJovana.request.CreatePostRequest;
import com.example.blogJovana.response.PostDetailsResponse;

import java.util.List;

public interface PostService {

    PostDetailsResponse getPostById(Long id);

    List<PostDetailsResponse> getAllPosts();

    List<PostDetailsResponse> getAllPostsForUser(String jwtEmail);

    PostDetailsResponse createPost(String jwtEmail, CreatePostRequest post);
}
