package com.homespot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "house_number")
public class HouseNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address_id")
    private Integer addressId;

    private String number;

    @ManyToOne
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Address address;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
