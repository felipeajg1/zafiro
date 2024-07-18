package com.app.zafiro.services.product;

import com.app.zafiro.exception.ResourceNotFoundException;
import com.app.zafiro.models.entity.material.Material;
import com.app.zafiro.repository.product.MaterialRepository;
import com.app.zafiro.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialServiceImpl implements IMaterialService {

    private static final String ENTITY_NAME = "Material";
    private static final String ENTITY_NOT_FOUND = "entity.not.found";

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    @Transactional
    public Material save(Material material) {
        return materialRepository.save(material);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Material> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return materialRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Material findById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MessageUtil.getMessage(ENTITY_NOT_FOUND, ENTITY_NAME, id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        materialRepository.findById(id)
                .ifPresentOrElse(
                        materialRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(MessageUtil.getMessage(ENTITY_NOT_FOUND, ENTITY_NAME, id));
                        }
                );
    }
}
