package com.example.blogJovana.controller;

import com.example.blogJovana.request.CreatePostRequest;
import com.example.blogJovana.response.PostDetailsResponse;
import com.example.blogJovana.service.PostService;
import com.example.blogJovana.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtServiceImpl jwtService;

    @GetMapping("/details/{id}")
    public ResponseEntity<PostDetailsResponse> getById(@PathVariable("id") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostDetailsResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostDetailsResponse> createPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                          @Valid @RequestBody CreatePostRequest postRequest) {
        String emailJWT = jwtService.extractEmail(jwt);
        return ResponseEntity.ok(postService.createPost(emailJWT, postRequest));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDetailsResponse> updatePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                          @PathVariable Long postId,
                                                          @Valid @RequestBody CreatePostRequest postRequest) {
        String emailJWT = jwtService.extractEmail(jwt);
        return ResponseEntity.ok(postService.updatePost(emailJWT, postId, postRequest));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostDetailsResponse>> getAllUserPosts(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        String emailJWT = jwtService.extractEmail(jwt);
        return ResponseEntity.ok(postService.getAllPostsForUser(emailJWT));
    }
}
