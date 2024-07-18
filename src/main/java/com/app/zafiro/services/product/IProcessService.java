package com.app.zafiro.services.product;

import com.app.zafiro.models.entity.cost.ProcessManufacture;
import org.springframework.data.domain.Page;

public interface IProcessService {

    Page<ProcessManufacture> findAll(int page, int size);

    ProcessManufacture findById(Long id);

    ProcessManufacture save(ProcessManufacture processManufacture);

    void delete(Long id);
}
