/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.query.datascheme.extend.DataFieldAdaptItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableAdaptItem<T> {
    private DesignDataTable dataTable;
    private DataTableMapDO dataTableMap;
    private List<DesignDataField> fields = new ArrayList<DesignDataField>();
    private List<DataFieldDeployInfo> fieldDeployInfos = new ArrayList<DataFieldDeployInfo>();
    private Map<String, DataFieldAdaptItem<T>> sourceKeySearch = new HashMap<String, DataFieldAdaptItem<T>>();
    private Map<String, DataFieldAdaptItem<T>> targetKeySearch = new HashMap<String, DataFieldAdaptItem<T>>();

    public DataTableAdaptItem(DesignDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public void addFieldItem(DataFieldAdaptItem<T> item) {
        this.fields.add(item.getField());
        this.fieldDeployInfos.add(item.getDeployInfo());
        this.targetKeySearch.put(item.getDeployInfo().getDataFieldKey(), item);
        if (item.getDeployInfo().getFieldName() != null) {
            this.sourceKeySearch.put(item.getDeployInfo().getFieldName().toUpperCase(), item);
        }
    }

    public DataTableMapDO getDataTableMap() {
        return this.dataTableMap;
    }

    public void setDataTableMap(DataTableMapDO dataTableMap) {
        this.dataTableMap = dataTableMap;
    }

    public DesignDataTable getDataTable() {
        return this.dataTable;
    }

    public List<DesignDataField> getFields() {
        return this.fields;
    }

    public List<DataFieldDeployInfo> getFieldDeployInfos() {
        return this.fieldDeployInfos;
    }

    public DataFieldAdaptItem<T> findBySourceKey(String sourceKey) {
        return this.sourceKeySearch.get(sourceKey.toUpperCase());
    }

    public DataFieldAdaptItem<T> findByTargetKey(String targetKey) {
        return this.targetKeySearch.get(targetKey);
    }
}

