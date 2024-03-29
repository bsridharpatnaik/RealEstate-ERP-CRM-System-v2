package com.ec.application.controller;

import java.util.List;

import com.ec.application.aspects.CheckAuthority;
import com.ec.application.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.ReusableClasses.ApiOnlyMessageAndCodeError;
import com.ec.application.model.InwardInventory;
import com.ec.application.service.InwardInventoryService;
import com.ec.common.Filters.FilterDataList;

@RestController
@RequestMapping("/inward")
public class InwardInventoryController {
    @Autowired
    InwardInventoryService iiService;

    @PostMapping("/create")
    @CheckAuthority
    @ResponseStatus(HttpStatus.CREATED)
    public InwardInventory createInwardInventory(@RequestBody InwardInventoryData payload) throws Exception {

        return iiService.createInwardnventory(payload);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ReturnInwardInventoryData fetchAllInwardInventory(@RequestBody FilterDataList filterDataList,
                                                             @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Direction.DESC) Pageable pageable)
            throws Exception {

        return iiService.fetchInwardnventory(filterDataList, pageable);
    }

    @PostMapping("/totals")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductGroupedDAO> fetchAllInwardInventoryTotals(@RequestBody FilterDataList filterDataList)
            throws Exception {

        return iiService.getTotalsForInward(filterDataList);
    }

    @PostMapping("/export")
    @ResponseStatus(HttpStatus.OK)
    public List<InwardInventoryExportDAO2> fetchAllInwardInventoryForExport2(@RequestBody FilterDataList filterDataList)
            throws Exception {

        return iiService.fetchInwardnventoryForExport2(filterDataList);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CheckAuthority
    public InwardInventory setRejectInwardInventory(@PathVariable Long id,
                                                    @RequestBody ReturnRejectInwardOutwardData rd) throws Exception {
        return iiService.addRejectInwardEntry(rd, id);
    }

    @GetMapping("/{id}")
    public InwardInventory findInwardInventoryById(@PathVariable long id) throws Exception {
        return iiService.findById(id);
    }

    @PutMapping("/{id}")
    @CheckAuthority
    public InwardInventory updateInwardInventoryById(@PathVariable long id, @RequestBody InwardInventoryData payload)
            throws Exception {
        return iiService.updateInwardnventory(payload, id);
    }

    @DeleteMapping(value = "/{id}")
    @CheckAuthority
    public ResponseEntity<?> deleteInwardInventoryById(@PathVariable Long id) throws Exception {

        iiService.deleteInwardInventoryById(id);
        return ResponseEntity.ok("Entity deleted");
    }

    @ExceptionHandler(
            {JpaSystemException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiOnlyMessageAndCodeError sqlError(Exception ex) {
        ApiOnlyMessageAndCodeError apiError = new ApiOnlyMessageAndCodeError(500,
                "Something went wrong while handling data. Contact Administrator.");
        return apiError;
    }
}
