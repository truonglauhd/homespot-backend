package com.homespot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EstateDTO {
    private Integer id;
    private BigDecimal area;
    private LocalDateTime createdAt;
    private String ownerName;
    private String estateType;
    private String address;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getEstateType() { return estateType; }
    public void setEstateType(String estateType) { this.estateType = estateType; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
