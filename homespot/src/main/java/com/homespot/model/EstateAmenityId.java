package com.homespot.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EstateAmenityId implements Serializable {
    private Integer estateId;
    private Integer amenityId;

    public EstateAmenityId() {}
    public EstateAmenityId(Integer estateId, Integer amenityId) {
        this.estateId = estateId;
        this.amenityId = amenityId;
    }

    public Integer getEstateId() { return estateId; }
    public void setEstateId(Integer estateId) { this.estateId = estateId; }
    public Integer getAmenityId() { return amenityId; }
    public void setAmenityId(Integer amenityId) { this.amenityId = amenityId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstateAmenityId)) return false;
        EstateAmenityId that = (EstateAmenityId) o;
        return Objects.equals(estateId, that.estateId) &&
                Objects.equals(amenityId, that.amenityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estateId, amenityId);
    }
}
