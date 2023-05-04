package com.example.springsecurityapplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Entity
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false, columnDefinition = "text", unique = true)
    @NotEmpty(message = "Наименование товара не может быть пустым")
    private String title;
    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Описание товара не может быть пустым")
    private String description;
    @Column(name = "price", nullable = false)
    @Min(value = 1, message = "Цена товара не может быть пустой, либо отрицательной")
    private float  price;
    @Column(name = "warehouse", nullable = false)
    @NotEmpty(message = "Склад по нахождению товара не может быть пустым")
    private String warehouse;

    @Column(name = "seller", nullable = false)
    @NotEmpty(message="Информация о продавце не может быть пустой")
    private String seller;
    @ManyToOne(optional = false)
    private Category category;

    private LocalDateTime dateTime;

    //Данный метод будет заполнять поле даты и времени при создании объекта класса
    @PrePersist
    private void init(){
        dateTime = LocalDateTime.now();
    }

}
