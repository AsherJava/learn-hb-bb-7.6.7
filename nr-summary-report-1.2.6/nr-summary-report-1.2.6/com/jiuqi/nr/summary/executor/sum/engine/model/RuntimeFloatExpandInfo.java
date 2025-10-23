/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.summary.executor.sum.engine.model;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuntimeFloatExpandInfo {
    private String dimensionName;
    private Map<String, String> keyConditionMap = new HashMap<String, String>();

    public RuntimeFloatExpandInfo(String dimensionName, List<IEntityRow> entityRows, String formula) {
        this.dimensionName = dimensionName;
        for (IEntityRow row : entityRows) {
            String key = row.getEntityKeyData();
            this.keyConditionMap.put(key, formula + "\"" + key + "\"");
        }
    }

    public List<String> getDimKeyList() {
        return new ArrayList<String>(this.keyConditionMap.keySet());
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public String getConditionFormula(String key) {
        return this.keyConditionMap.get(key);
    }
}

