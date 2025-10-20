/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.api.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jiuqi.ent.taskhandler")
public class TaskHandlerProperties {
    private String mainAddress;
    private Map<String, String> subAddresses;

    public String getMainAddress() {
        return this.mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Map<String, String> getSubAddresses() {
        return this.subAddresses;
    }

    public void setSubAddresses(Map<String, String> subAddresses) {
        this.subAddresses = subAddresses;
    }
}

