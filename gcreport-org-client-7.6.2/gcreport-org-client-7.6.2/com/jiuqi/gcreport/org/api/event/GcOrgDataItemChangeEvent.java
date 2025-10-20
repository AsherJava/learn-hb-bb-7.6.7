/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.event;

import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent;

public class GcOrgDataItemChangeEvent
extends GcOrgDataCacheBaseEvent {
    private static final long serialVersionUID = 1L;
    private String cacheName;

    public GcOrgDataItemChangeEvent(String orgType) {
        this.setCacheName(orgType);
    }

    public String getCacheName() {
        return this.cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}

