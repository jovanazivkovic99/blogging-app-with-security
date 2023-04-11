package com.example.blogJovana.mapper;

import com.example.blogJovana.model.Comment;
import com.example.blogJovana.request.CommentDetailsRequest;
import com.example.blogJovana.response.CommentDetailsResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(imports = {LocalDateTime.class}, componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "commentId", source = "id")
    CommentDetailsResponse toCommentDetailsResponse(Comment comment);
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Comment toComment(CommentDetailsRequest request);

    List<CommentDetailsResponse> toCommentDetailsList(List<Comment> comments);
}
