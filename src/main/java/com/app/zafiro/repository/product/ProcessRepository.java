package com.app.zafiro.repository.product;

import com.app.zafiro.models.entity.cost.ProcessManufacture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<ProcessManufacture, Long> {
}
