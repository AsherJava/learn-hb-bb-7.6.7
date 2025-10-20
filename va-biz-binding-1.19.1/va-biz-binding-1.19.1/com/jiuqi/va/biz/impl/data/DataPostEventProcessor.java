/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataImpl;

public class DataPostEventProcessor {
    private final DataImpl dataImpl;

    public DataPostEventProcessor(DataImpl dataImpl) {
        this.dataImpl = dataImpl;
    }

    public void beforeSave() {
        this.dataImpl.dataPostEvents.forEach(event -> event.beforeSave(this.dataImpl));
    }

    public void afterSave() {
        this.dataImpl.dataPostEvents.forEach(event -> event.afterSave(this.dataImpl));
    }

    public void beforeDelete() {
        this.dataImpl.canload = false;
        try {
            this.dataImpl.dataPostEvents.forEach(event -> event.beforeDelete(this.dataImpl));
        }
        finally {
            this.dataImpl.canload = true;
        }
    }

    public void afterDelete() {
        this.dataImpl.dataPostEvents.forEach(event -> event.afterDelete(this.dataImpl));
    }
}

