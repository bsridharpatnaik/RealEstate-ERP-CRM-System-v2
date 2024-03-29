package com.ec.application.service;

import javax.transaction.Transactional;

import com.ec.application.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CheckBeforeDeleteService {

    @Autowired
    InwardInventoryRepo inwardInventoryRepo;

    @Autowired
    OutwardInventoryRepo outwardInventoryRepo;

    @Autowired
    LostDamagedInventoryRepo lostDamagedInventoryRepo;

    @Autowired
    MachineryOnRentRepo machineryOnRentRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    StockRepo stockRepo;

    @Autowired
    WarehouseRepo warehouseRepo;

    @Autowired
    UsageAreaRepo usageAreaRepo;

    @Autowired
    InwardOutwardListRepo inwardOutwardListRepo;

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    BOQInventoryMappingRepo bimRepo;

    @Autowired
    InventoryMonthPriceMappingRepository inventoryMonthPriceMappingRepository;

    Logger log = LoggerFactory.getLogger(ContactService.class);

    public boolean isContactUsed(Long contactId) throws Exception {
        if (outwardInventoryRepo.contractorUsageCount(contactId) > 0
                || machineryOnRentRepo.supplierUsageCount(contactId) > 0
                || inwardInventoryRepo.supplierUsageCount(contactId) > 0)
            return true;
        else
            return false;

    }

    public boolean isProductUsed(Long productId) throws Exception {
        if (stockRepo.productUsageCount(productId) > 0 || lostDamagedInventoryRepo.productUsageCount(productId) > 0
                || inwardOutwardListRepo.productUsageCount(productId) > 0 || bimRepo.productUsageCount(productId) > 0
                || inventoryMonthPriceMappingRepository.productUsageCount(productId) > 0)
            return true;
        else
            return false;

    }

    public boolean isProductNotUsedButStockExists(Long productId) throws Exception {
        if (stockRepo.productUsageCount(productId) > 0 &&
                lostDamagedInventoryRepo.productUsageCount(productId) == 0 &&
                inwardOutwardListRepo.productUsageCount(productId) == 0 &&
                bimRepo.productUsageCount(productId) == 0)
            return true;
        else
            return false;

    }

    public boolean isCategoryUsed(Long categoryId) throws Exception {
        if (productRepo.categoryUsageCount(categoryId) > 0)
            return true;
        else
            return false;
    }

    public boolean isMachineryUsed(Long machineryId) throws Exception {
        if (machineryOnRentRepo.machineryUsageCount(machineryId) > 0)
            return true;
        else
            return false;
    }

    public boolean isLocationUsed(Long locationId) throws Exception {
        if (machineryOnRentRepo.locationUsageCount(locationId) > 0
                || outwardInventoryRepo.locationUsageCount(locationId) > 0
                || bimRepo.locationUsageCount(locationId) > 0)
            return true;
        else
            return false;
    }

    public boolean isWarehouseUsed(String warehouseName) throws Exception {
        if (stockRepo.warehouseUsageCount(warehouseName) > 0
                || lostDamagedInventoryRepo.warehouseUsageCount(warehouseName) > 0
                || inwardInventoryRepo.warehouseUsageCount(warehouseName) > 0
                || outwardInventoryRepo.warehouseUsageCount(warehouseName) > 0)
            return true;
        else
            return false;
    }

    public boolean isUsageAreaUsed(Long id) {
        if (outwardInventoryRepo.usageAreaUsageCount(id) > 0)
            return true;
        else
            return false;
    }

    public boolean isSupplierUsed(Long id) {
        if (inwardInventoryRepo.supplierUsageCount(id) > 0 || machineryOnRentRepo.supplierUsageCount(id) > 0)
            return true;
        else
            return false;
    }

    public boolean isContractorUsed(Long id) {
        if (outwardInventoryRepo.contractorUsageCount(id) > 0)
            return true;
        else
            return false;
    }

    public boolean isBuildingTypeUsed(Long id) {
        if (locationRepo.getBuildingTypeUsageCount(id) > 0 || bimRepo.buildingTypeUsageCount(id) > 0)
            return true;
        else
            return false;
    }
}
