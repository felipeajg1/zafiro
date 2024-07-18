package com.app.zafiro.controller;

import com.app.zafiro.models.entity.material.Material;
import com.app.zafiro.services.product.IMaterialService;
import com.app.zafiro.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/material")
public class MaterialRestController {

    private static final Logger logger = LoggerFactory.getLogger(MaterialRestController.class);
    private static final String MESSAGE = "message";
    private static final String MATERIAL = "Material";

    @Autowired
    @Qualifier("materialServiceImpl")
    private IMaterialService materialService;

    //create and update Material
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> createMaterial(@RequestBody Material material) {
        logger.info("Start the method createMaterial");
        Map<String, Object> response = new HashMap<>();
        material = materialService.save(material);
        response.put(MATERIAL, material);
        logger.info("Material create or update with id: {}", material.getId());
        logger.info("End the method createMaterial");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByIdMaterial(@PathVariable Long id) {
        logger.info("Start the method findByIdMaterial");
        Map<String, Object> response = new HashMap<>();
        Material material = materialService.findById(id);
        response.put(MATERIAL, material);
        logger.info("the material with id {} was found", id);
        logger.info("End the method findByIdMaterial");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMaterial(@PathVariable Long id) {
        logger.info("Start the method deleteMaterial");
        Map<String, Object> response = new HashMap<>();
        materialService.delete(id);
        response.put(MESSAGE, MessageUtil.getMessage("entity.delete", MATERIAL, id));
        logger.info("Material removed with id: {}", id);
        logger.info("End the method deleteMaterial");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMaterials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Start the method getAllMaterials");
        Map<String, Object> response = new HashMap<>();
        Page<Material> materialPage = materialService.findAll(page, size);
        response.put(MATERIAL, materialPage);
        logger.info("Materials in list {}", materialPage.getSize());
        logger.info("End the method getAllMaterials");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
