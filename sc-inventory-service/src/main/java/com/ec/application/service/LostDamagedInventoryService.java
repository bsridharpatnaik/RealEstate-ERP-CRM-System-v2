package com.ec.application.service;

import java.text.ParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ec.application.ReusableClasses.ReusableMethods;
import com.ec.application.data.CreateLostOrDamagedInventoryData;
import com.ec.application.data.LostDamagedReturnData;
import com.ec.application.model.LostDamagedInventory;
import com.ec.application.repository.LostDamagedInventoryRepo;
import com.ec.application.repository.ProductRepo;
import com.ec.application.repository.StockRepo;
import com.ec.application.repository.WarehouseRepo;
import com.ec.common.Filters.FilterDataList;
import com.ec.common.Filters.LostDamagedInventorySpecification;

@Service
@Transactional
public class LostDamagedInventoryService {
    @Autowired
    LostDamagedInventoryRepo lostDamagedInventoryRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductService productService;

    @Autowired
    StockService stockService;

    @Autowired
    StockRepo stockRepo;

    @Autowired
    WarehouseRepo warehouseRepo;

    @Autowired
    PopulateDropdownService populateDropdownService;

    @Autowired
    InventoryNotificationService inventoryNotificationService;

    Logger log = LoggerFactory.getLogger(LostDamagedInventoryService.class);

    @Transactional(rollbackFor = Exception.class)
    public LostDamagedInventory createData(CreateLostOrDamagedInventoryData payload) throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        LostDamagedInventory lostDamagedInventory = new LostDamagedInventory();
        validatePayload(payload);
        populateData(lostDamagedInventory, payload);
        Double closingStock = adjustStockBeforeCreate(payload);
        lostDamagedInventory.setClosingStock(closingStock);
        inventoryNotificationService.pushQuantityEditedNotification(lostDamagedInventory.getProduct(),
                lostDamagedInventory.getWarehouse().getWarehouseName(), "lostdamagedadded",
                lostDamagedInventory.getQuantity());
        return lostDamagedInventoryRepo.save(lostDamagedInventory);
    }

    public LostDamagedReturnData findFiilteredostDamagedList(FilterDataList filterDataList, Pageable pageable)
            throws ParseException {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        Specification<LostDamagedInventory> spec = LostDamagedInventorySpecification.getSpecification(filterDataList);

        LostDamagedReturnData lostDamagedReturnData = new LostDamagedReturnData();
        if (spec != null)
            lostDamagedReturnData.setLostDamagedInventories(lostDamagedInventoryRepo.findAll(spec, pageable));
        else
            lostDamagedReturnData.setLostDamagedInventories(lostDamagedInventoryRepo.findAll(pageable));
        lostDamagedReturnData.setLdDropdown(populateDropdownService.fetchData("lostdamaged"));
        return lostDamagedReturnData;
    }

    @Transactional(rollbackFor = Exception.class)
    private Double adjustStockBeforeCreate(CreateLostOrDamagedInventoryData payload) throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        String warehouseName = warehouseRepo.findById(payload.getWarehouseId()).get().getWarehouseName();
        return stockService.updateStock(payload.getProductId(), warehouseName, payload.getQuantity(), "outward");
    }

    private void populateData(LostDamagedInventory lostDamagedInventory, CreateLostOrDamagedInventoryData payload)
            throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        lostDamagedInventory.setLocationOfTheft(payload.getTheftLocation());
        lostDamagedInventory.setQuantity(payload.getQuantity());
        lostDamagedInventory.setProduct(productService.findSingleProduct(payload.getProductId()));
        lostDamagedInventory.setDate(payload.getDate());
        lostDamagedInventory.setWarehouse(warehouseRepo.findById(payload.getWarehouseId()).get());
        lostDamagedInventory.setFileInformations(ReusableMethods.convertFilesListToSet(payload.getFileInformations()));
        lostDamagedInventory.setAdditionalComment(payload.getAdditionalComment());
    }

    @Transactional(rollbackFor = Exception.class)
    public LostDamagedInventory UpdateData(CreateLostOrDamagedInventoryData payload, Long id) throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        Optional<LostDamagedInventory> lostDamagedInventoryOpt = lostDamagedInventoryRepo.findById(id);
        validatePayload(payload);
        if (!lostDamagedInventoryOpt.isPresent())
            throw new Exception("Lost Damaged inventory by ID " + id + " Not found");
        LostDamagedInventory lostDamagedInventory = lostDamagedInventoryOpt.get();
        Double oldStock = lostDamagedInventory.getQuantity();
        AdjustStockBeforeDelete(lostDamagedInventory);
        populateData(lostDamagedInventory, payload);
        lostDamagedInventory.setClosingStock(adjustStockBeforeCreate(payload));
        if (!oldStock.equals(lostDamagedInventory.getQuantity()))
            inventoryNotificationService.pushQuantityEditedNotification(lostDamagedInventory.getProduct(),
                    lostDamagedInventory.getWarehouse().getWarehouseName(), "lostdamagedmodified",
                    lostDamagedInventory.getQuantity());

        return lostDamagedInventoryRepo.save(lostDamagedInventory);
    }

    private void validatePayload(CreateLostOrDamagedInventoryData payload) throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        if (!productRepo.findById(payload.getProductId()).isPresent())
            throw new Exception("Invalid Product ID");
        if (!warehouseRepo.findById(payload.getWarehouseId()).isPresent())
            throw new Exception("Invalid warehouse ID");
        if (payload.getQuantity() <= 0)
            throw new Exception("Quantity should be greater than zero");
    }

    public LostDamagedInventory findById(Long id) throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        Optional<LostDamagedInventory> lostDamagedInventoryOpt = lostDamagedInventoryRepo.findById(id);
        if (!lostDamagedInventoryOpt.isPresent())
            throw new Exception("Lost Damaged Inventory by ID " + id + " Not found");
        LostDamagedInventory lostDamagedInventory = lostDamagedInventoryOpt.get();
        return lostDamagedInventory;
    }

    @Transactional(rollbackFor = Exception.class)
    public void DeleteData(Long id) throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        Optional<LostDamagedInventory> lostDamagedInventoryOpt = lostDamagedInventoryRepo.findById(id);
        if (!lostDamagedInventoryOpt.isPresent())
            throw new Exception("Machinery On rent by ID " + id + " Not found");
        LostDamagedInventory lostDamagedInventory = lostDamagedInventoryOpt.get();
        AdjustStockBeforeDelete(lostDamagedInventory);
        lostDamagedInventoryRepo.softDeleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    private void AdjustStockBeforeDelete(LostDamagedInventory lostDamagedInventory) throws Exception {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        stockService.updateStock(lostDamagedInventory.getProduct().getProductId(),
                lostDamagedInventory.getWarehouse().getWarehouseName(), lostDamagedInventory.getQuantity(), "inward");
    }

    public Page<LostDamagedInventory> findAll(Pageable pageable) {
        log.info("Invoked - " + new Throwable().getStackTrace()[0].getMethodName());
        Page<LostDamagedInventory> allLODInv = lostDamagedInventoryRepo.findAll(pageable);
        return allLODInv;
    }

}
