package com.ats.resumebuilder.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> skills;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 1000)
    private List<String> experience;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> education;

    public UserProfile() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<String> getExperience() { return experience; }
    public void setExperience(List<String> experience) { this.experience = experience; }

    public List<String> getEducation() { return education; }
    public void setEducation(List<String> education) { this.education = education; }
}
