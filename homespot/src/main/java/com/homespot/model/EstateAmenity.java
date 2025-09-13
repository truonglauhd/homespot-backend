package com.homespot.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estate_amenities")
public class EstateAmenity implements Serializable {

    @EmbeddedId
    private EstateAmenityId id;

    @ManyToOne
    @MapsId("estateId")
    @JoinColumn(name = "estate_id", insertable = false, updatable = false)
    private Estate estate;

    @ManyToOne
    @MapsId("amenityId")
    @JoinColumn(name = "amenity_id", insertable = false, updatable = false)
    private Amenity amenity;

    public EstateAmenityId getId() { return id; }
    public void setId(EstateAmenityId id) { this.id = id; }
    public Estate getEstate() { return estate; }
    public void setEstate(Estate estate) { this.estate = estate; }
    public Amenity getAmenity() { return amenity; }
    public void setAmenity(Amenity amenity) { this.amenity = amenity; }
}
