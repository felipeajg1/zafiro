package com.app.zafiro.models.entity.cost;

import com.app.zafiro.models.entity.interfaces.ICost;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class ProcessManufacture extends ICost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "hourly_cost")
    private Double hourlyCost;

    @Column(name = "process_quantity")
    private int processQuantity;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "cost_product")
    private Double costProduct;

    @Column(name = "cost_process")
    private Double costProcess;

    @Column(name = "double_cost")
    private boolean doubleCalculate;

    @Override
    public Double getCost() {
        return this.costProduct;
    }
}
