package com.homespot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ward_id")
    private Integer wardId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "ward_id", insertable = false, updatable = false)
    private Ward ward;

    @OneToMany(mappedBy = "address")
    private List<HouseNumber> houseNumbers;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getWardId() { return wardId; }
    public void setWardId(Integer wardId) { this.wardId = wardId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Ward getWard() { return ward; }
    public void setWard(Ward ward) { this.ward = ward; }
    public List<HouseNumber> getHouseNumbers() { return houseNumbers; }
    public void setHouseNumbers(List<HouseNumber> houseNumbers) { this.houseNumbers = houseNumbers; }
}
