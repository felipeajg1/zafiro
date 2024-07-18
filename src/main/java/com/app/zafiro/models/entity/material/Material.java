package com.app.zafiro.models.entity.material;

import com.app.zafiro.models.entity.interfaces.ICost;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Material extends ICost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String color;

    @Column(name = "unit_cost")
    private Double unitCost;

    @Column(name = "total_cost")
    private Double totalCost;
    private String photo;

    @Override
    public Double getCost() {
        return this.unitCost;
    }
}
