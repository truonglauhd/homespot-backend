package com.homespot.service;

import com.homespot.dto.PropertyDTO;
import com.homespot.model.Address;
import com.homespot.model.Estate;
import com.homespot.model.EstateImage;
import com.homespot.model.Post;
import com.homespot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PostRepository postRepository;

    public List<PropertyDTO> listProperties() {
        return postRepository.findAll().stream()
                .map(this::toPropertyDTO)
                .collect(Collectors.toList());
    }

    private PropertyDTO toPropertyDTO(Post post) {
        Estate estate = post.getEstate();
        Address address = estate != null ? estate.getAddress() : null;
        String location = address != null ? address.getName() : null;
        String type = estate != null && estate.getEstateType() != null ? estate.getEstateType().getName() : null;

        String imageUrl = null;
        if (estate != null && estate.getImages() != null && !estate.getImages().isEmpty()) {
            EstateImage first = estate.getImages().get(0);
            if (first != null) {
                imageUrl = first.getImageUrl();
            }
        }

        return new PropertyDTO(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getPrice(),
                estate != null ? estate.getArea() : null,
                location,
                type,
                imageUrl
        );
    }
}

