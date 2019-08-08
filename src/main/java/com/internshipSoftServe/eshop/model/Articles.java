package com.internshipSoftServe.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Data
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    @Size(max = 45)
    private String name;

    @Column(length = 255)
    @Size(max = 255)
    private String text;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getProductId (){
        return product.getId();
    }
    public void setProductId (long productId){
        this.product = new Product();
        this.product.setId(productId);
    }


    public Articles() {
    }

    public Articles(@Size(max = 45) String name, @Size(max = 255) String text, LocalDateTime created_at) {
        this.name = name;
        this.text = text;
        this.created_at = created_at;
    }
}
