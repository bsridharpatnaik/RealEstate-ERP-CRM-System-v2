package com.ec.crm.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.ec.crm.Data.ApiOnlyMessageAndCodeError;
import com.ec.crm.Data.LeadCreateData;
import com.ec.crm.Data.LeadDAO;
import com.ec.crm.Data.LeadDetailInfo;
import com.ec.crm.Data.LeadListWithTypeAheadData;
import com.ec.crm.Data.UserReturnData;
import com.ec.crm.Enums.ActivityTypeEnum;
import com.ec.crm.Enums.LeadStatusEnum;
import com.ec.crm.Enums.PropertyTypeEnum;
import com.ec.crm.Filters.FilterDataList;
import com.ec.crm.Model.Lead;
import com.ec.crm.Service.LeadService;
import com.ec.crm.Service.UserDetailsService;

@RestController
@RequestMapping(value = "/lead", produces =
        {"application/json", "text/json"})
public class LeadController {
    @Autowired
    LeadService leadService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping
    public Page<Lead> returnAllLeads(Pageable pageable) {
        return leadService.fetchAll(pageable);
    }

    @GetMapping("{id}")
    public LeadDAO returnSingleLead(@PathVariable Long id) throws Exception {
        UserReturnData currentUser = userDetailsService.getCurrentUser();
        request.setAttribute("currentUser", currentUser);
        return leadService.getSingleLead(id);
    }

    @GetMapping("/purpose")
    public List<String> returnPurposeList() throws Exception {
        return leadService.getPurposeList();
    }


    @GetMapping("/history/{id}")
    public List<Lead> findLeadHistory(@PathVariable long id) throws Exception {
        return leadService.history(id);
    }

    @GetMapping("/getallinfo/{id}")
    public LeadDetailInfo findLeadDetailInfoByID(@PathVariable long id) throws Exception {
        return leadService.findSingleLeadDetailInfo(id);
    }

    @GetMapping("/allowedactivitytype/{id}")
    public List<ActivityTypeEnum> getAllowedActivities(@PathVariable Long id) throws Exception {
        return leadService.getAllowedActivities(id);
    }

    @GetMapping("/validPropertyTypes")
    public List<String> findValidPropertyTypes() {
        return PropertyTypeEnum.getValidPropertyType();
    }

    @GetMapping("/validLeadStatus")
    public List<String> findValidLeadStatus() {
        return LeadStatusEnum.getValidLeadStatus();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Lead createLead(@Valid @RequestBody LeadCreateData payload) throws Exception {
        return leadService.createLead(payload);
    }

    @PutMapping("/{id}")
    public Lead updateLead(@PathVariable Long id, @RequestBody LeadCreateData payload) throws Exception {
        return leadService.updateLead(payload, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchLead(@PathVariable Long id, @RequestBody HashMap payload) throws Exception {
        leadService.patchLead(id, payload);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->
        {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
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
