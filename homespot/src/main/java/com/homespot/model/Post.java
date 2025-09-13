package com.homespot.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estate_id")
    private Integer estateId;

    private String title;
    private String description;
    private BigDecimal price;

    @Column(name = "post_at")
    private LocalDateTime postAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "estate_id", insertable = false, updatable = false)
    private Estate estate;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getEstateId() { return estateId; }
    public void setEstateId(Integer estateId) { this.estateId = estateId; }
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
    public Estate getEstate() { return estate; }
    public void setEstate(Estate estate) { this.estate = estate; }
}
