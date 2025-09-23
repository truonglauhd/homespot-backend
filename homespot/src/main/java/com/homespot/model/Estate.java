package com.homespot.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

@Entity
@Table(name = "estate")
public class Estate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "owner_id")
    private Integer ownerId;

    @ManyToOne
    @JoinColumn(name = "estate_type_id", insertable = false, updatable = false)
    private EstateType estateType;

    @Column(name = "estate_type_id")
    private Integer estateTypeId;

    @Column(name = "address_id")
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Address address;

    private BigDecimal area;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "estate")
    private List<EstateImage> images;

    @ManyToMany
    @JoinTable(name = "estate_amenities",
            joinColumns = @JoinColumn(name = "estate_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<Amenity> amenities;

    @OneToMany(mappedBy = "estate")
    private List<EstateFeature> features;

    @OneToMany(mappedBy = "estate")
    private List<AgentHistory> agentHistories;
    // Add these fields to your Estate class:
@Column(name = "status")
private String status;

@Column(name = "description") 
private String description;

@Column(name = "price")
private BigDecimal price;

@Column(name = "agent_id")
private Integer agentId;

// Add these getter/setter methods:
public String getStatus() { return status; }
public void setStatus(String status) { this.status = status; }

public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }

public BigDecimal getPrice() { return price; }
public void setPrice(BigDecimal price) { this.price = price; }

public Integer getAgentId() { return agentId; }
public void setAgentId(Integer agentId) { this.agentId = agentId; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }
    public Integer getEstateTypeId() { return estateTypeId; }
    public void setEstateTypeId(Integer estateTypeId) { this.estateTypeId = estateTypeId; }
    public EstateType getEstateType() { return estateType; }
    public void setEstateType(EstateType estateType) { this.estateType = estateType; }
    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Set<Amenity> getAmenities() { return amenities; }
    public void setAmenities(Set<Amenity> amenities) { this.amenities = amenities; }
    public List<EstateImage> getImages() { return images; }
    public void setImages(List<EstateImage> images) { this.images = images; }
    public List<EstateFeature> getFeatures() { return features; }
    public void setFeatures(List<EstateFeature> features) { this.features = features; }
    public List<AgentHistory> getAgentHistories() { return agentHistories; }
    public void setAgentHistories(List<AgentHistory> agentHistories) { this.agentHistories = agentHistories; }
}