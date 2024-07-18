package com.app.zafiro.models.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;
    private String name;
    private String color;
    private Double cost;
    private String photo;
}
