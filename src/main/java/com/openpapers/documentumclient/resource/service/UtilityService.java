package com.openpapers.documentumclient.resource.service;

import com.openpapers.documentumclient.resource.model.HealthMetric;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@Service
public class UtilityService {

    public HealthMetric heartBeat() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        HealthMetric healthMetric = new HealthMetric(runtimeMXBean.getUptime());
        return healthMetric;
    }
}
