/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.event;

import java.util.List;
import org.springframework.context.ApplicationEvent;

public class SyncVchrTableEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -319948518655606528L;
    private String tenantName;
    private List<String> assistFlagList;

    public SyncVchrTableEvent(Object source, String tenantName, List<String> assistFlagList) {
        super(source);
        this.tenantName = tenantName;
        this.assistFlagList = assistFlagList;
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public List<String> getAssistFlagList() {
        return this.assistFlagList;
    }

    public void setAssistFlagList(List<String> assistFlagList) {
        this.assistFlagList = assistFlagList;
    }
}

