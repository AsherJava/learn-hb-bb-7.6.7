/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import java.util.List;
import java.util.Map;

public interface DataUpdate {
    public List<Map<String, Object>> getInsert();

    public List<Map<String, Object>> getUpdate();

    public List<Map<String, Object>> getDelete();

    default public boolean isEmpty() {
        return this.getInsert().size() == 0 && this.getUpdate().size() == 0 && this.getDelete().size() == 0;
    }
}

