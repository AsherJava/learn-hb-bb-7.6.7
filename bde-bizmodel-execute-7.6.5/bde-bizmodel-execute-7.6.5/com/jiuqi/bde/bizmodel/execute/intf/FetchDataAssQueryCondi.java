/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 */
package com.jiuqi.bde.bizmodel.execute.intf;

import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import java.util.List;
import java.util.Map;

public class FetchDataAssQueryCondi {
    private List<String> sumFields;
    private List<String> groupFields;
    private List<Dimension> whereFields;
    private List<String> fetchTypesFields;
    private Map<String, ColumnTypeEnum> floatColumnsType;

    public FetchDataAssQueryCondi(List<String> SumFields, List<String> groupFields, List<Dimension> whereFields) {
        this.sumFields = SumFields;
        this.groupFields = groupFields;
        this.whereFields = whereFields;
    }

    public List<String> getFetchTypesFields() {
        return this.fetchTypesFields;
    }

    public void setFetchTypesFields(List<String> fetchTypesFields) {
        this.fetchTypesFields = fetchTypesFields;
    }

    public List<String> getSumFields() {
        return this.sumFields;
    }

    public void setSumFields(List<String> sumFields) {
        this.sumFields = sumFields;
    }

    public List<String> getGroupFields() {
        return this.groupFields;
    }

    public void setGroupFields(List<String> groupFields) {
        this.groupFields = groupFields;
    }

    public List<Dimension> getWhereFields() {
        return this.whereFields;
    }

    public void setWhereFields(List<Dimension> whereFields) {
        this.whereFields = whereFields;
    }

    public Map<String, ColumnTypeEnum> getFloatColumnsType() {
        return this.floatColumnsType;
    }

    public void setFloatColumnsType(Map<String, ColumnTypeEnum> floatColumnsType) {
        this.floatColumnsType = floatColumnsType;
    }
}

