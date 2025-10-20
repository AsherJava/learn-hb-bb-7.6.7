/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.progress.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ProgressProperties {
    public static final String PROPERTY_EXECUTOR_NAME = "custom.progress.executor.name";
    @Value(value="${custom.progress.executor.name:redis}")
    private String executorName;

    public String getExecutorName() {
        return this.executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }
}

