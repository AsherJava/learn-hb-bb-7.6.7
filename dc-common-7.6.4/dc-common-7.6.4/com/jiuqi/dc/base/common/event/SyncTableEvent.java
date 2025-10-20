/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.event;

import org.springframework.context.ApplicationEvent;

public class SyncTableEvent
extends ApplicationEvent {
    private String tenantName;
    private static final long serialVersionUID = -886832711390057754L;

    public SyncTableEvent(Object source, String tenantName) {
        super(source);
        this.tenantName = tenantName;
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}

