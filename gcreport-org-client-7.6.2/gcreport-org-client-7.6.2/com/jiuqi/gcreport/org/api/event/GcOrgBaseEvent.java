/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.event;

import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import org.springframework.context.ApplicationEvent;

public class GcOrgBaseEvent<T>
extends ApplicationEvent {
    private String key;
    private EventChangeTypeEnum eventChangeTypeEnum;
    private static final long serialVersionUID = 1L;

    public GcOrgBaseEvent(EventChangeTypeEnum ect, String cacheKey, T values) {
        super(values);
        this.eventChangeTypeEnum = ect;
        this.key = cacheKey;
    }

    public T getSource() {
        return (T)super.getSource();
    }

    public String getCacheKey() {
        return this.key;
    }

    public boolean isInsert() {
        return this.eventChangeTypeEnum == EventChangeTypeEnum.INSERT;
    }

    public boolean isUpdate() {
        return this.eventChangeTypeEnum == EventChangeTypeEnum.INSERT;
    }

    public boolean isDelete() {
        return this.eventChangeTypeEnum == EventChangeTypeEnum.INSERT;
    }
}

