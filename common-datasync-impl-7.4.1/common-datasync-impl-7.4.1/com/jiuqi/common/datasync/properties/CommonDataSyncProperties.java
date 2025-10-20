/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class CommonDataSyncProperties {
    @Value(value="${spring.application.name:}")
    private String serviceName;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

