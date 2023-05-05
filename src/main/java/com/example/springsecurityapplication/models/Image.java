package com.example.springsecurityapplication.models;

import jakarta.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String filename;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;

    public Image(int id, String filename, Product product) {
        this.id = id;
        this.filename = filename;
        this.product = product;
    }

    public Image() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
