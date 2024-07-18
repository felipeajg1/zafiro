package com.app.zafiro.services.product;

import com.app.zafiro.exception.ResourceNotFoundException;
import com.app.zafiro.models.entity.cost.ProcessManufacture;
import com.app.zafiro.repository.product.ProcessRepository;
import com.app.zafiro.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessServiceImpl implements IProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private static final String ENTITY_NAME = "Process Manufacture";
    private static final String ENTITY_NOT_FOUND = "entity.not.found";

    private final ProcessRepository processRepository;

    @Autowired
    public ProcessServiceImpl(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    @Override
    @Transactional
    public ProcessManufacture save(ProcessManufacture processManufacture) {
        logger.info("Saving or updating the process: {}", processManufacture);
        Double costProduct = calculateCostProcess(processManufacture.getHourlyCost(),
                processManufacture.getProductQuantity());

        Double costProcess = processManufacture.isDoubleCalculate()
                ? calculateCostProcess(processManufacture.getHourlyCost(), processManufacture.getProcessQuantity())
                : costProduct;

        processManufacture.setCostProduct(costProduct);
        processManufacture.setCostProcess(costProcess);
        return processRepository.save(processManufacture);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessManufacture> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return processRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessManufacture findById(Long id) {
        return processRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MessageUtil.getMessage(ENTITY_NOT_FOUND, ENTITY_NAME, id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        processRepository.findById(id)
                .ifPresentOrElse(
                        processRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(MessageUtil.getMessage(ENTITY_NOT_FOUND, ENTITY_NAME, id));
                        }
                );
    }

    private Double calculateCostProcess(Double hourlyCost, int hourlyQuantity) {
        if (hourlyQuantity == 0) {
            throw new IllegalArgumentException(MessageUtil.getMessage("error.number.zero"));
        }
        return (hourlyCost/hourlyQuantity);
    }
}
