/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.ref;

import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import java.util.Map;

public interface RefTableDataProvider {
    public int getRefTableType();

    default public Map<String, Object> convertDimValues(String tableName, Map<String, Object> dimValues) {
        return null;
    }

    default public RefTableDataMap getRefTableDataMap(String tableName, Map<String, Object> dimValues) {
        return null;
    }
}

