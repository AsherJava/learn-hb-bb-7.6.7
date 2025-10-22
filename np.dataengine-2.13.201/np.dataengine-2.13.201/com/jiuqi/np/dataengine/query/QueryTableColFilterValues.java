/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.np.dataengine.common.QueryField;
import java.util.HashMap;
import java.util.List;

public class QueryTableColFilterValues
extends HashMap<String, List<Object>> {
    private static final long serialVersionUID = 4712267156277358076L;

    public final List<Object> getColFilterValues(QueryField queryField) {
        List result = (List)this.get(queryField.getUID());
        return result;
    }

    public final void addColFilterValues(QueryField queryField, List<Object> filterValues) {
        List<Object> result = (List<Object>)this.get(queryField.getUID());
        if (result != null && result.size() > 0) {
            result.retainAll(filterValues);
        } else {
            result = filterValues;
        }
        this.put(queryField.getUID(), result);
    }
}

