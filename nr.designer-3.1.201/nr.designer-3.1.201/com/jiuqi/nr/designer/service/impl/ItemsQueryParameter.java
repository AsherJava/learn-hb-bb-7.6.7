/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.ArrayList;
import java.util.List;

public class ItemsQueryParameter {
    private String tableKey;
    private DimensionValueSet valueSet;
    private List<FieldDefine> queryFields;
    private String filter;

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public DimensionValueSet getValueSet() {
        return this.valueSet;
    }

    public void setValueSet(DimensionValueSet valueSet) {
        this.valueSet = valueSet;
    }

    public List<FieldDefine> getQueryFields() {
        return this.queryFields;
    }

    public void setQueryFields(List<FieldDefine> queryFields) {
        this.queryFields = queryFields;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void addField(FieldDefine fieldDefine) {
        if (this.queryFields == null) {
            this.queryFields = new ArrayList<FieldDefine>();
        }
        this.queryFields.add(fieldDefine);
    }

    public static ItemsQueryParameter buildDefaultQueryParam(String tableKey, IDataDefinitionRuntimeController runtimeCtrl) throws JQException {
        ItemsQueryParameter parameter = new ItemsQueryParameter();
        parameter.setTableKey(tableKey);
        try {
            parameter.setQueryFields(runtimeCtrl.getAllFieldsInTable(tableKey));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        parameter.setValueSet(new DimensionValueSet());
        return parameter;
    }
}

