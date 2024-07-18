package com.app.zafiro.models.entity.product;

import com.app.zafiro.models.entity.material.Material;
import com.app.zafiro.models.entity.cost.ProcessManufacture;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_manufacture")
public class ProductManufacture extends Product {

    private Integer number;

    @Column(name = "material_cost")
    private Double materialCost;

    @Column(name = "manufactured_cost")
    private Double manufacturedCost;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "material_product",
            joinColumns = @JoinColumn(name = "product_manufacture_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private Set<Material> materials = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "process_product",
            joinColumns = @JoinColumn(name = "product_manufacture_id"),
            inverseJoinColumns = @JoinColumn(name = "process_id")
    )
    private Set<ProcessManufacture> processes = new HashSet<>();
}
