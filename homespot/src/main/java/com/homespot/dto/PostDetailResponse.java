package com.homespot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PostDetailResponse {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDateTime postAt;
    private LocalDateTime expiredAt;
    
    private Integer estateId;
    private Integer estateTypeId;
    private String estateTypeName;
    private Integer addressId;
    private String address;
    private BigDecimal area;
    private String imageUrl;
    
    private List<AmenityDTO> amenities;
    
    private List<FeatureDTO> features;
    
    private Integer agentId;
    private String agentName;
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public LocalDateTime getPostAt() { return postAt; }
    public void setPostAt(LocalDateTime postAt) { this.postAt = postAt; }
    
    public LocalDateTime getExpiredAt() { return expiredAt; }
    public void setExpiredAt(LocalDateTime expiredAt) { this.expiredAt = expiredAt; }
    
    public Integer getEstateId() { return estateId; }
    public void setEstateId(Integer estateId) { this.estateId = estateId; }
    
    public Integer getEstateTypeId() { return estateTypeId; }
    public void setEstateTypeId(Integer estateTypeId) { this.estateTypeId = estateTypeId; }
    
    public String getEstateTypeName() { return estateTypeName; }
    public void setEstateTypeName(String estateTypeName) { this.estateTypeName = estateTypeName; }
    
    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public List<AmenityDTO> getAmenities() { return amenities; }
    public void setAmenities(List<AmenityDTO> amenities) { this.amenities = amenities; }
    
    public List<FeatureDTO> getFeatures() { return features; }
    public void setFeatures(List<FeatureDTO> features) { this.features = features; }
    
    public Integer getAgentId() { return agentId; }
    public void setAgentId(Integer agentId) { this.agentId = agentId; }
    
    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }
    
    // Nested DTOs
    public static class AmenityDTO {
        private Integer id;
        private String name;
        
        public AmenityDTO() {}
        
        public AmenityDTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    public static class FeatureDTO {
        private Integer id;
        private String name;
        private String value;
        
        public FeatureDTO() {}
        
        public FeatureDTO(Integer id, String name, String value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}
