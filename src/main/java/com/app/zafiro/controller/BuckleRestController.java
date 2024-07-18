package com.app.zafiro.controller;

import com.app.zafiro.models.entity.material.Buckle;
import com.app.zafiro.services.product.IBuckleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/buckle")
public class BuckleRestController {

    private static final Logger logger = LoggerFactory.getLogger(BuckleRestController.class);
    private static final String BUCKLE = "Buckle";

    @Autowired
    @Qualifier("buckleServiceImpl")
    private IBuckleService buckleService;

    //create and update Material
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> createBuckle(@RequestBody Buckle buckle) {
        logger.info("Start the createBuckle");
        Map<String, Object> response = new HashMap<>();
        buckle = (Buckle) buckleService.save(buckle);
        response.put(BUCKLE, buckle);
        logger.info("Buckle create or update with id: {}", buckle.getId());
        logger.info("End the method createBuckle");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
