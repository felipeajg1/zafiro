package com.app.zafiro.services.product;

import com.app.zafiro.models.entity.material.Material;
import org.springframework.data.domain.Page;

public interface IMaterialService {

    Page<Material> findAll(int page, int size);

    Material findById(Long id);

    Material save(Material material);

    void delete(Long id);
}
