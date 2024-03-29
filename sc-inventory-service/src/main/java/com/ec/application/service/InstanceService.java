package com.ec.application.service;

import com.ec.application.data.InstanceList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InstanceService {
    @Value("${spring.profiles.active}")
    private String profile;

    public InstanceList getInstance() {
        if (profile.contains("ec-"))
            return InstanceList.egcity;
        else if (profile.contains("sc-"))
            return InstanceList.suncity;
        return null;
    }
}
