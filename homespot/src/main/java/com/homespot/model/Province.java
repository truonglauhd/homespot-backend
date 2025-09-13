package com.homespot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "province")
    private List<Ward> wards;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Ward> getWards() { return wards; }
    public void setWards(List<Ward> wards) { this.wards = wards; }
}
