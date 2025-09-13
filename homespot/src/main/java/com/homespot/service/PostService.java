package com.homespot.service;

import com.homespot.dto.PostRequest;
import com.homespot.dto.PostResponse;
import com.homespot.mapper.PostMapper;
import com.homespot.model.Estate;
import com.homespot.model.Post;
import com.homespot.repository.EstateRepository;
import com.homespot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired private PostRepository postRepository;
    @Autowired private EstateRepository estateRepository;

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Integer id) {
        return postRepository.findById(id)
                .map(PostMapper::toResponse)
                .orElse(null);
    }

    public PostResponse createPost(PostRequest request) {
        Estate estate = estateRepository.findById(request.getEstateId())
                .orElseThrow(() -> new RuntimeException("Estate not found"));
        Post post = PostMapper.toEntity(request, estate);
        Post saved = postRepository.save(post);
        return PostMapper.toResponse(saved);
    }
}
