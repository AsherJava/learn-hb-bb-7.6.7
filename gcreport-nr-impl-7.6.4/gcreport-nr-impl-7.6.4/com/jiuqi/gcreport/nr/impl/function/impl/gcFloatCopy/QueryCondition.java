/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcSimpleCopyOperand;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;
import java.util.Map;

public class QueryCondition {
    private List<String> queryColumns = CollectionUtils.newArrayList();
    private List<Integer> keyColumns = CollectionUtils.newArrayList();
    private String filter = null;
    private FieldDefine floatOrderField;
    private String RegionKey;
    private Map<Integer, Integer> queryColumnTypes = CollectionUtils.newHashMap();

    public List<String> getQueryColumns() {
        return this.queryColumns;
    }

    public List<Integer> getKeyColumns() {
        return this.keyColumns;
    }

    public String getFilter() {
        return this.filter;
    }

    public FieldDefine getFloatOrderField() {
        return this.floatOrderField;
    }

    public String getRegionKey() {
        return this.RegionKey;
    }

    public void setFloatOrderField(FieldDefine floatOrderField) {
        this.floatOrderField = floatOrderField;
    }

    public void setRegionKey(String regionKey) {
        this.RegionKey = regionKey;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setKeyColumns(String[] keyFields) {
        if (keyFields == null) {
            return;
        }
        for (String field : keyFields) {
            this.addKeyColumn(field);
        }
    }

    public void addKeyColumn(String keyField) {
        int index = this.addColumn(keyField);
        if (index > -1) {
            this.keyColumns.add(index);
        }
    }

    public int addColumn(String keyField) {
        int index = -1;
        if (keyField == null) {
            index = -1;
        } else if (this.queryColumns.contains(keyField = new GcSimpleCopyOperand(keyField).formatField())) {
            index = this.queryColumns.indexOf(keyField);
        } else {
            this.queryColumns.add(keyField);
            index = this.queryColumns.size() - 1;
        }
        return index;
    }

    public void addQueryColumns(List<String> keyFields) {
        keyFields.forEach(keyField -> {
            if (!this.queryColumns.contains(keyField)) {
                this.queryColumns.add((String)keyField);
            }
        });
    }

    public Map<Integer, Integer> getQueryColumnTypes() {
        return this.queryColumnTypes;
    }

    public void addQueryColumnType(Integer index, Integer columnType) {
        this.queryColumnTypes.put(index, columnType);
    }
}

