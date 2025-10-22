/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DimQueryInfo {
    private String dimension;
    private String entityId;
    private String refTableName;
    private String refFieldName;
    private List<Object> values;
    private Set<Object> valueSet;
    private Map<String, Object> refMap;
    private Object variableValue;

    public DimQueryInfo(String dimension) {
        this.dimension = dimension;
    }

    public void initForJudge(DimensionTable dimensionTable) {
        if (this.values != null) {
            this.valueSet = new HashSet<Object>(this.values);
        }
        this.refMap = new HashMap<String, Object>();
        for (int i = 0; i < dimensionTable.rowCount(); ++i) {
            DimensionRow row = dimensionTable.getRow(i);
            String keyValue = row.getKey();
            Object refDimValue = row.getValue(this.getRefFieldName());
            this.refMap.put(keyValue, refDimValue);
        }
    }

    public boolean judge(String unitKey, Object value) {
        if (this.valueSet != null && this.valueSet.contains(value)) {
            return true;
        }
        Object refValue = this.refMap.get(unitKey);
        return refValue != null && refValue.equals(value);
    }

    public String getDimension() {
        return this.dimension;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public String getRefTableName() {
        return this.refTableName;
    }

    public String getRefFieldName() {
        return this.refFieldName;
    }

    public List<Object> getValues() {
        return this.values;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public void setRefFieldName(String refFieldName) {
        this.refFieldName = refFieldName;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public Object getVariableValue() {
        return this.variableValue;
    }

    public void setVariableValue(Object variableValue) {
        this.variableValue = variableValue;
    }

    public String toString() {
        return "DimQueryInfo [dimension=" + this.dimension + ", entityId=" + this.entityId + ", refTableName=" + this.refTableName + ", refFieldName=" + this.refFieldName + ", values=" + this.values + "]";
    }
}

