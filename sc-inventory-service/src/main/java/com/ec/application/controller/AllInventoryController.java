package com.ec.application.controller;

import com.ec.application.data.InventoryReportByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import com.ec.application.ReusableClasses.ApiOnlyMessageAndCodeError;
import com.ec.application.data.AllInventoryReturnData;
import com.ec.application.service.AllInventoryService;
import com.ec.common.Filters.FilterDataList;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class AllInventoryController {
    @Autowired
    AllInventoryService allInventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AllInventoryReturnData fetchAllInwardInventory(@RequestBody FilterDataList filterDataList,
                                                          @PageableDefault(page = 0, size = 10, sort =
                                                                  {"date", "type", "keyid"}, direction = Direction.DESC) Pageable pageable) throws Exception {
        return allInventoryService.fetchAllInventory(filterDataList, pageable);
    }

    @GetMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public HashMap<String, String> refreshAllInventoryData() {
        HashMap<String, String> result = new HashMap<>();
        allInventoryService.refreshAllInventoryData();
        result.put("Message", "Data refresh triggered. Please check back after sometime.");
        return result;
    }

    @PostMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryReportByDate> getInventoryReport(@RequestBody FilterDataList filterDataList) throws Exception {
        return allInventoryService.getInventoryReport(filterDataList);
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