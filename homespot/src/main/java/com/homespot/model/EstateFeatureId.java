package com.homespot.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EstateFeatureId implements Serializable {
    private Integer estateId;
    private Integer featureId;

    public EstateFeatureId() {}
    public EstateFeatureId(Integer estateId, Integer featureId) {
        this.estateId = estateId;
        this.featureId = featureId;
    }

    public Integer getEstateId() { return estateId; }
    public void setEstateId(Integer estateId) { this.estateId = estateId; }
    public Integer getFeatureId() { return featureId; }
    public void setFeatureId(Integer featureId) { this.featureId = featureId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstateFeatureId)) return false;
        EstateFeatureId that = (EstateFeatureId) o;
        return Objects.equals(estateId, that.estateId) &&
                Objects.equals(featureId, that.featureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estateId, featureId);
    }
}
