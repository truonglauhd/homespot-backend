package com.homespot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estate_images")
public class EstateImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estate_id")
    private Integer estateId;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "estate_id", insertable = false, updatable = false)
    private Estate estate;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getEstateId() { return estateId; }
    public void setEstateId(Integer estateId) { this.estateId = estateId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Estate getEstate() { return estate; }
    public void setEstate(Estate estate) { this.estate = estate; }
}
