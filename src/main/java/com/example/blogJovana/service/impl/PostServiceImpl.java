package com.example.blogJovana.service.impl;

import com.example.blogJovana.exceptions.CategoryNotFoundException;
import com.example.blogJovana.exceptions.PostNotFoundException;
import com.example.blogJovana.exceptions.UnauthorizedActionException;
import com.example.blogJovana.exceptions.UserNotFoundException;
import com.example.blogJovana.mapper.PostMapper;
import com.example.blogJovana.model.Category;
import com.example.blogJovana.model.Post;
import com.example.blogJovana.model.User;
import com.example.blogJovana.repository.CategoryRepository;
import com.example.blogJovana.repository.PostRepository;
import com.example.blogJovana.repository.UserRepository;
import com.example.blogJovana.request.CreatePostRequest;
import com.example.blogJovana.response.PostDetailsResponse;
import com.example.blogJovana.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PostDetailsResponse getPostById(Long id) {

        Post post = postRepository.getReferenceById(id);
        return postMapper
                .toPostDetails(post);
    }

    @Override
    public List<PostDetailsResponse> getAllPosts() {

        List<Post> allPosts = postRepository.findAll();
        return postMapper.toPostDetailsList(allPosts);
    }

    @Override
    public PostDetailsResponse createPost(String jwtEmail, CreatePostRequest request) {
        User user = userRepository.findByEmail(jwtEmail).orElseThrow(
                () -> new UserNotFoundException(jwtEmail)
        );
        Category postCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
        Post post = postMapper.toPost(request);
        post.setUser(user);
        post.setCategory(postCategory);
        Post savedPost = postRepository.save(post);
        return postMapper.toPostDetails(savedPost);
    }

    @Override
    public PostDetailsResponse updatePost(String jwtEmail, Long postId, CreatePostRequest request) {
        /*User user = userRepository.findByEmail(jwtEmail).orElseThrow(
                () -> new UserNotFoundException(jwtEmail)
        );*/

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        isUserPostOwner(post, jwtEmail);
        Category postCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
        //Post post = postMapper.toPost(request);
        //post.setUser(user);
        post.setCategory(postCategory);
        post.setTitle(request.title());
        post.setContent(request.content());
        Post updatedPost = postRepository.save(post);
        return postMapper.toPostDetails(updatedPost);
    }

    @Override
    public List<PostDetailsResponse> getAllPostsForUser(String jwtEmail) {

        User user = userRepository.findByEmail(jwtEmail).orElseThrow(
                () -> new UserNotFoundException(jwtEmail)
        );

        List<Post> userPosts = postRepository.findByUserId(user.getId());
        return postMapper.toPostDetailsList(userPosts);
    }

    private void authorize(String emailJWT, String email) {
        if (!email.equals(emailJWT))
            throw new UnauthorizedActionException(email);
    }

    private void isUserPostOwner(Post post, String jwtEmail) {
        if (!post.getUser().getEmail().equals(jwtEmail)) {
            throw new UnauthorizedActionException(jwtEmail);
        }
    }
    /*private void authorizeUser(String email, User property, String actionMessage) {
        User expectedOwner = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(actionMessage, "error.user.not-found", email, HttpStatus.NOT_FOUND));
        if (!property.getOwner().equals(expectedOwner))
            throw new UnauthorizedActionException(actionMessage, "error.user.unauthorized-property-action",
                    expectedOwner.getEmail(), property.getId(), HttpStatus.UNAUTHORIZED);
    }*/
}
