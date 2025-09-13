package com.homespot.mapper;

import com.homespot.dto.PostRequest;
import com.homespot.dto.PostResponse;
import com.homespot.model.Estate;
import com.homespot.model.Post;

public class PostMapper {

    public static Post toEntity(PostRequest dto, Estate estate) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setType(dto.getType());
        post.setPrice(dto.getPrice());
        post.setExpiredAt(dto.getExpiredAt());
        post.setEstate(estate);
        return post;
    }

    public static PostResponse toResponse(Post post) {
        PostResponse dto = new PostResponse();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setType(post.getType());
        dto.setPrice(post.getPrice());
        dto.setPostAt(post.getPostAt());
        dto.setExpiredAt(post.getExpiredAt());
        dto.setEstateId(post.getEstate().getId());
        return dto;
    }
}
