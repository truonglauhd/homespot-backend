package com.homespot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estate_features")
public class EstateFeature {

    @EmbeddedId
    private EstateFeatureId id;

    @ManyToOne
    @MapsId("estateId")
    @JoinColumn(name = "estate_id", insertable = false, updatable = false)
    private Estate estate;

    @ManyToOne
    @MapsId("featureId")
    @JoinColumn(name = "feature_id", insertable = false, updatable = false)
    private Feature feature;

    private String value;

    public EstateFeatureId getId() { return id; }
    public void setId(EstateFeatureId id) { this.id = id; }
    public Estate getEstate() { return estate; }
    public void setEstate(Estate estate) { this.estate = estate; }
    public Feature getFeature() { return feature; }
    public void setFeature(Feature feature) { this.feature = feature; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
