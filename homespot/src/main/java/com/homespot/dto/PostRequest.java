package com.homespot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PostRequest {
    private String title;
    private String description;
    private String type;
    private BigDecimal price;
    private LocalDateTime expiredAt;
    private Integer estateId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public LocalDateTime getExpiredAt() { return expiredAt; }
    public void setExpiredAt(LocalDateTime expiredAt) { this.expiredAt = expiredAt; }
    public Integer getEstateId() { return estateId; }
    public void setEstateId(Integer estateId) { this.estateId = estateId; }
}
