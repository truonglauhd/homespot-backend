package com.homespot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EstateDTO {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal area;
    private Integer bedrooms;
    private Integer bathrooms;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String ownerName;
    private String estateType;
    private Integer estateTypeId;
    private String address;
    private String location;
    private Integer userId;
    private List<FeatureDTO> features;
    private List<AmenityDTO> amenities;

    public List<FeatureDTO> getFeatures() { return features; }
    public void setFeatures(List<FeatureDTO> features) { this.features = features; }
    public List<AmenityDTO> getAmenities() { return amenities; }
    public void setAmenities(List<AmenityDTO> amenities) { this.amenities = amenities; }

    // Basic getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getEstateType() { return estateType; }
    public void setEstateType(String estateType) { this.estateType = estateType; }

    public Integer getEstateTypeId() { return estateTypeId; }
    public void setEstateTypeId(Integer estateTypeId) { this.estateTypeId = estateTypeId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}