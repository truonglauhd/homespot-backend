package com.homespot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PostResponse {
    private Integer id;
    private String title;
    private String description;
    private String type;
    private BigDecimal price;
    private LocalDateTime postAt;
    private LocalDateTime expiredAt;
    private Integer estateId;
    private Integer estateTypeId;
    private String address;
    private BigDecimal area;
    private String imageUrl;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
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
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
