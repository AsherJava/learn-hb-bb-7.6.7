/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.model.Model;
import java.util.Map;

public interface GlobalDataTransEvent {
    public void onSaveAfterCommit(Model var1, DataImpl var2, Map<String, DataUpdate> var3);
}

