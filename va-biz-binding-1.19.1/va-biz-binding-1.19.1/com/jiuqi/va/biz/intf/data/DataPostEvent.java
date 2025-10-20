/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.impl.data.DataImpl;

public interface DataPostEvent {
    default public int getOrder() {
        return 0;
    }

    public void beforeSave(DataImpl var1);

    public void afterSave(DataImpl var1);

    public void beforeDelete(DataImpl var1);

    public void afterDelete(DataImpl var1);
}

