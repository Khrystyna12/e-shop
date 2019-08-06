package com.internshipSoftServe.eshop.model;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "users")

@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 45)
    @Size(max = 45)
    private String username;

    @Column(length = 255)
    @Size(max = 255)
    private byte[] password;
    
    @Column(length = 255)
    @Size(max = 255)
    private String first_name;
    
    @Column(length = 255)
    @Size(max = 255)
    private String last_name;
    
    @Column(length = 255)
    @Size(max = 255)
    private String email;

    private boolean active;

    private Date created_at;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    
    public enum Role{
        admin,
        user;
    }
}
