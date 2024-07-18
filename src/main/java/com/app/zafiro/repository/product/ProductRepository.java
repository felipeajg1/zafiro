package com.app.zafiro.repository.product;

import com.app.zafiro.models.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
