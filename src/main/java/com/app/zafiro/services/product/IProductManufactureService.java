package com.app.zafiro.services.product;

import com.app.zafiro.models.dto.ProductManufactureDTO;
import com.app.zafiro.models.entity.product.ProductManufacture;
import org.springframework.data.domain.Page;

public interface IProductManufactureService {

    Page<ProductManufacture> findAll(int page, int size);

    ProductManufacture findById(Long id);

    ProductManufacture save(ProductManufactureDTO productManufactureDTO);

    void delete(Long id);
}
