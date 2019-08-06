package com.internshipSoftServe.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    @Size(max = 255)
    private String text;

    private LocalDateTime created_at;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private User user;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Product product;
    
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Comment comment;
    
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
