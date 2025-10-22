/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.job;

import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractActuator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SameCtrlExtractRegistry {
    private final Map<String, SameCtrlExtractActuator> serviceValue2ActuatorMap;

    @Autowired
    public SameCtrlExtractRegistry(Map<String, SameCtrlExtractActuator> serviceValue2ActuatorMap) {
        this.serviceValue2ActuatorMap = serviceValue2ActuatorMap;
    }

    public SameCtrlExtractActuator getSameCtrlExtractActuator(String virtualOrgType) {
        return this.serviceValue2ActuatorMap.get(virtualOrgType);
    }
}

