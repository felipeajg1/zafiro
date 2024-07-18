package com.app.zafiro.services.product;

import com.app.zafiro.repository.product.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuckleServiceImpl extends MaterialServiceImpl implements IBuckleService {

    @Autowired
    public BuckleServiceImpl(MaterialRepository materialRepository) {
        super(materialRepository);
    }
}
