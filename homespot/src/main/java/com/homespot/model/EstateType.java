package com.homespot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estate_types")
public class EstateType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
