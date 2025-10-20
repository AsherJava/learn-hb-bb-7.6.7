/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.intf.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfigProperties {
    @Value(value="${jiuqi.dc.serviceIdenti.serviceName:DC}")
    private String serviceName;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

