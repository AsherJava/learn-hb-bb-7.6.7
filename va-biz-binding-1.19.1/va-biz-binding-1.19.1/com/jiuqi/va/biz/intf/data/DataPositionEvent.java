/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;

@Deprecated
public interface DataPositionEvent
extends DataPostEvent {
    @Override
    public void afterSave(DataImpl var1);

    @Override
    public void afterDelete(DataImpl var1);
}

