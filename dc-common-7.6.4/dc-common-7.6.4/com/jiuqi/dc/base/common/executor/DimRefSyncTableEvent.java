/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.executor;

import org.springframework.context.ApplicationEvent;

public class DimRefSyncTableEvent
extends ApplicationEvent {
    private String tenantName;
    private static final long serialVersionUID = 4721374235068237902L;

    public DimRefSyncTableEvent(Object source, String tenantName) {
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

