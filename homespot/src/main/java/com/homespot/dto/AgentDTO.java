package com.homespot.dto;

public class AgentDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String profilePicture;
    private String bio;
    private Integer experienceYears;
    private Integer successfulSales;

    public AgentDTO(Long id, String name, String email, String phone, 
                   String profilePicture, String bio, Integer experienceYears, 
                   Integer successfulSales) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.experienceYears = experienceYears;
        this.successfulSales = successfulSales;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getProfilePicture() { return profilePicture; }
    public String getBio() { return bio; }
    public Integer getExperienceYears() { return experienceYears; }
    public Integer getSuccessfulSales() { return successfulSales; }
}