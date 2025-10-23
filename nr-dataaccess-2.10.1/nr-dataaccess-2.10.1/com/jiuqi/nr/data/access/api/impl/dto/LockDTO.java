/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.access.api.impl.dto;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LockDTO {
    private int lockIndex = -1;
    private Map<ArrayKey, List<INvwaDataRow>> rowMap;
    private Set<String> accessUnit;
    private Map<String, Map<String, Boolean>> formAuth;

    public int getLockIndex() {
        return this.lockIndex;
    }

    public void setLockIndex(int lockIndex) {
        this.lockIndex = lockIndex;
    }

    public void initRowMap(INvwaDataSet dataSet, List<ColumnModelDefine> keys) {
        this.rowMap = new HashMap<ArrayKey, List<INvwaDataRow>>();
        for (INvwaDataRow iNvwaDataRow : dataSet) {
            Object[] arr = new Object[keys.size()];
            for (int i = 0; i < keys.size(); ++i) {
                Object value;
                ColumnModelDefine columnModelDefine = keys.get(i);
                arr[i] = value = iNvwaDataRow.getValue(columnModelDefine);
            }
            ArrayKey arrayKey = new ArrayKey(arr);
            this.rowMap.computeIfAbsent(arrayKey, k -> new ArrayList()).add(iNvwaDataRow);
        }
    }

    public boolean rowMapInit() {
        return this.rowMap != null;
    }

    public List<INvwaDataRow> getRows(ArrayKey key) {
        return this.rowMap.get(key);
    }

    public Set<String> getAccessUnit() {
        return this.accessUnit;
    }

    public void setAccessUnit(Set<String> accessUnit) {
        this.accessUnit = accessUnit;
    }

    public Map<String, Map<String, Boolean>> getFormAuth() {
        return this.formAuth;
    }

    public void setFormAuth(Map<String, Map<String, Boolean>> formAuth) {
        this.formAuth = formAuth;
    }
}

