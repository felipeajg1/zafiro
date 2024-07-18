package com.app.zafiro.controller;

import com.app.zafiro.models.dto.ProductManufactureDTO;
import com.app.zafiro.models.entity.product.ProductManufacture;
import com.app.zafiro.services.product.IProductManufactureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/productManufacture")
public class ProductManufactureRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductManufactureRestController.class);
    private static final String PRODUCT_MANUFACTURE = "ProductManufacture";

    @Autowired
    private IProductManufactureService productService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductManufactureDTO productManufactureDTO) {
        logger.info("Start the method createProduct");
        Map<String, Object> response = new HashMap<>();
        ProductManufacture productManufacture = productService.save(productManufactureDTO);
        response.put(PRODUCT_MANUFACTURE, productManufacture);
        logger.info("Product Manufacture create or update with id: {}", productManufacture.getId());
        logger.info("End the method createProduct");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByIdProduct(@PathVariable Long id) {
        logger.info("Start the method findByIdProduct");
        Map<String, Object> response = new HashMap<>();
        ProductManufacture productManufacture = productService.findById(id);
        response.put(PRODUCT_MANUFACTURE, productManufacture);
        logger.info("The Product Manufacture with id {} was found", id);
        logger.info("End the method findByIdProduct");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        logger.info("Start the method deleteProduct");
        productService.delete(id);
        logger.info("Product Manufacture removed with id: {}", id);
        logger.info("End the method deleteProduct");
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Start the method getAllProducts");
        Map<String, Object> response = new HashMap<>();
        Page<ProductManufacture> productManufacturePage = productService.findAll(page, size);
        response.put(PRODUCT_MANUFACTURE, productManufacturePage);
        logger.info("Products Manufacture in list {}", productManufacturePage.getSize());
        logger.info("End the method getAllProducts");
        return ResponseEntity.ok(response);
    }
}
