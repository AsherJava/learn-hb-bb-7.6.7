/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import java.util.Map;

public interface DataTransEvent {
    public void onSaveAfterCommit(DataImpl var1, Map<String, DataUpdate> var2);
}

