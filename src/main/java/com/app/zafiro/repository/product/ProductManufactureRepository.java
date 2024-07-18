package com.app.zafiro.repository.product;

import com.app.zafiro.models.entity.product.ProductManufacture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductManufactureRepository extends JpaRepository<ProductManufacture, Long> {

    int countByNameAndColor(String name, String color);

}
