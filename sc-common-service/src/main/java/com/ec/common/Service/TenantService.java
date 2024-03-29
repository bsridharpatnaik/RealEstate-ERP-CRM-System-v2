package com.ec.common.Service;

import com.ec.common.Data.TenantInformation;
import com.ec.common.Model.Tenant;
import com.ec.common.Repository.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {

    @Autowired
    UserService userService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    TenantRepo tenantRepo;

    public List<TenantInformation> fetchTenantList() {
        List<TenantInformation> tenants = new ArrayList<TenantInformation>();
        for (Tenant t : tenantRepo.findAll(Sort.by(Sort.Direction.DESC, "name"))) {
            tenants.add(new TenantInformation(t.getTenantLongName(),t.getName(),t.getInventory(),t.getCrm()));
        }
        return tenants;
    }

    public List<String> getValidTenantKeys() {
        List<String> validTenants = new ArrayList<String>();
        for (Tenant t : tenantRepo.findAll()) {
            validTenants.add(t.getName());
        }
        return validTenants;
    }

    public List<TenantInformation> getAllowedTenants() throws Exception {
        List<Tenant> allowedTenantKeys = userService.findTenantsForCurrentUser();
        allowedTenantKeys = allowedTenantKeys.stream().sorted(Comparator.comparing(Tenant::getName)).collect(Collectors.toList());
        List<TenantInformation> allowedTenants = new ArrayList<TenantInformation>();
        for (Tenant ti : allowedTenantKeys) {
                allowedTenants.add(new TenantInformation(
                        ti.getTenantLongName(),
                        ti.getName(),
                        ti.getInventory(),
                        ti.getCrm()));
        }
        return allowedTenants;
    }
}