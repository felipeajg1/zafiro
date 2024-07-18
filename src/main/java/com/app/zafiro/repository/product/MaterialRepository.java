package com.app.zafiro.repository.product;

import com.app.zafiro.models.entity.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
