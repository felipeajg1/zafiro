package com.app.zafiro.services.product;

import com.app.zafiro.exception.ResourceNotFoundException;
import com.app.zafiro.models.dto.ProductManufactureDTO;
import com.app.zafiro.models.entity.cost.ProcessManufacture;
import com.app.zafiro.models.entity.interfaces.ICost;
import com.app.zafiro.models.entity.material.Material;
import com.app.zafiro.models.entity.product.ProductManufacture;
import com.app.zafiro.repository.product.MaterialRepository;
import com.app.zafiro.repository.product.ProcessRepository;
import com.app.zafiro.repository.product.ProductManufactureRepository;
import com.app.zafiro.util.MessageUtil;
import com.app.zafiro.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class ProductManufactureServiceImpl implements IProductManufactureService {

    private static final String ENTITY_NAME = "Product Manufacture";
    private static final String ENTITY_NOT_FOUND = "entity.not.found";

    private final ProductManufactureRepository productRepository;
    private final MaterialRepository materialRepository;
    private final ProcessRepository processRepository;

    @Autowired
    public ProductManufactureServiceImpl(
            ProductManufactureRepository productRepository,
            MaterialRepository materialRepository,
            ProcessRepository processRepository) {
        this.productRepository = productRepository;
        this.materialRepository = materialRepository;
        this.processRepository = processRepository;
    }

    @Override
    @Transactional
    public ProductManufacture save(ProductManufactureDTO productManufactureDTO) {
        ProductManufacture productManufacture =
                productManufactureDTO.getProductManufacture().getId() == null
                        ? saveProductManufacture(productManufactureDTO.getProductManufacture())
                        : updateProductManufacture(productManufactureDTO.getProductManufacture());

        // Associate materials and processes
        Set<Material> materials = new HashSet<>(materialRepository.findAllById(productManufactureDTO.getMaterials()));
        Set<ProcessManufacture> processes = new HashSet<>(processRepository.findAllById(productManufactureDTO.getProcess()));

        // Clear old references and add new ones
        productManufacture.getMaterials().clear();
        productManufacture.getProcesses().clear();
        productManufacture.getMaterials().addAll(materials);
        productManufacture.getProcesses().addAll(processes);

        // Calculate costs
        productManufacture.setMaterialCost(calculateTotalCost(materials));
        productManufacture.setManufacturedCost(calculateTotalCost(processes));
        productManufacture.setCost(productManufacture.getMaterialCost() + productManufacture.getManufacturedCost());
        return productRepository.save(productManufacture);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductManufacture> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductManufacture findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MessageUtil.getMessage(ENTITY_NOT_FOUND, ENTITY_NAME, id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(MessageUtil.getMessage(ENTITY_NOT_FOUND, ENTITY_NAME, id));
                        }
                );
    }

    private ProductManufacture saveProductManufacture(ProductManufacture productManufacture) {
        // Generate unique code
        String code = generateUniqueCode(productManufacture);
        productManufacture.setCode(code);
        return productManufacture;
    }

    private ProductManufacture updateProductManufacture(ProductManufacture productManufacture) {
        // Load existing entity from the database
        return productRepository.findById(productManufacture.getId())
                .map(existingProduct -> {
                    if (!Objects.equals(existingProduct.getName().trim(), productManufacture.getName().trim()) ||
                            !Objects.equals(existingProduct.getColor().trim(), productManufacture.getColor().trim())) {
                        existingProduct.setCode(generateUniqueCode(productManufacture));
                    }
                    existingProduct.setName(productManufacture.getName());
                    existingProduct.setNumber(productManufacture.getNumber());
                    existingProduct.setColor(productManufacture.getColor());
                    existingProduct.setPhoto(productManufacture.getPhoto());
                    return existingProduct;
                }).orElseThrow(() ->
                        new ResourceNotFoundException(MessageUtil.getMessage(ENTITY_NOT_FOUND, ENTITY_NAME, productManufacture.getId())));
    }

    private String generateUniqueCode(ProductManufacture productManufacture) {
        return ProductUtil.generateUniqueCode(productManufacture.getName(),
                productManufacture.getColor(),
                productRepository.countByNameAndColor(
                        productManufacture.getName(),
                        productManufacture.getColor()));
    }

    private Double calculateTotalCost(Set<? extends ICost> items) {
        return items.stream().
                mapToDouble(ICost::getCost).
                sum();
    }

}
