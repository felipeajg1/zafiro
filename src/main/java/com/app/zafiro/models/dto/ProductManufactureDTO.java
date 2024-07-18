package com.app.zafiro.models.dto;

import com.app.zafiro.models.entity.product.ProductManufacture;
import lombok.Data;

import java.util.List;

@Data
public class ProductManufactureDTO {

    private ProductManufacture productManufacture;
    private List<Long> materials;
    private List<Long> process;
}
