package com.homespot.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreateEstateRequest {
    public Integer estateTypeId;
    public Integer provinceId;
    public Integer wardId;
    public Integer addressId;
    public Integer houseNumberId; 
    public BigDecimal area;
    public List<Integer> amenityIds; 
    public List<FeatureValue> features;
    public List<String> imageUrls; 

    public static class FeatureValue {
        public Integer id;
        public String value;
    }
}


