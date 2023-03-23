package com.example.blogJovana.mapper;

import com.example.blogJovana.model.Post;
import com.example.blogJovana.request.CreatePostRequest;
import com.example.blogJovana.response.PostDetailsResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(imports = {LocalDateTime.class}, componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PostMapper {

    PostDetailsResponse toPostDetails(Post post);

    List<PostDetailsResponse> toPostDetailsList(List<Post> post);

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Post toPost(CreatePostRequest request);
}
