package com.homespot.controller;

import com.homespot.dto.PostRequest;
import com.homespot.dto.PostResponse;
import com.homespot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import com.homespot.repository.PostRepository;
import com.homespot.model.Post;
import com.homespot.mapper.PostMapper;
import com.homespot.dto.PostDetailResponse;
import com.homespot.mapper.PostDetailMapper;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired private PostService postService;

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/search")
    public Page<PostResponse> searchPosts(
            @RequestParam(required = false) Integer estateTypeId,
            @RequestParam(required = false) Integer provinceId,
            @RequestParam(required = false) Integer wardId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable
    ) {
        Page<Post> posts = postRepository.search(estateTypeId, provinceId, wardId, minPrice, maxPrice, pageable);
        return posts.map(PostMapper::toResponse);
    }


    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    @GetMapping("/{id}/detail")
    public PostDetailResponse getPostDetail(@PathVariable Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return PostDetailMapper.toDetailResponse(post);
    }

    @PostMapping
    public PostResponse createPost(@RequestBody PostRequest request) {
        return postService.createPost(request);
    }
}
