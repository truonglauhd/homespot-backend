package com.homespot.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "amenities")
    private Set<Estate> estates;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Estate> getEstates() { return estates; }
    public void setEstates(Set<Estate> estates) { this.estates = estates; }
}
