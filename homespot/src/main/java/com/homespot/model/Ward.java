package com.homespot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ward")
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "province_id")
    private Integer provinceId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id", insertable = false, updatable = false)
    private Province province;

    @OneToMany(mappedBy = "ward")
    private List<Address> addresses;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getProvinceId() { return provinceId; }
    public void setProvinceId(Integer provinceId) { this.provinceId = provinceId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }
    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
}
