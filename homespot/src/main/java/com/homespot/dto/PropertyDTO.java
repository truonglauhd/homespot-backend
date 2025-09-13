package com.homespot.dto;

import java.math.BigDecimal;

public class PropertyDTO {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal area;
    private String location;
    private String type;
    private String image;

    public PropertyDTO() {}

    public PropertyDTO(Integer id,
                        String title,
                        String description,
                        BigDecimal price,
                        BigDecimal area,
                        String location,
                        String type,
                        String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.area = area;
        this.location = location;
        this.type = type;
        this.image = image;
    }

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
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}

