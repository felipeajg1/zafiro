package com.app.zafiro.models.entity.product;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ProductNotManufacture extends Product {

    private String brand;
    private String gender;
}
