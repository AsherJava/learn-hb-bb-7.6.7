/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import java.util.List;
import java.util.Map;

public class CustomFetchResult {
    private List<Object[]> datas;
    private Map<String, Integer> columnsMap;
    private Map<String, ColumnTypeEnum> fieldType;

    public Map<String, Integer> getColumnsMap() {
        return this.columnsMap;
    }

    public void setColumnsMap(Map<String, Integer> columnsMap) {
        this.columnsMap = columnsMap;
    }

    public Map<String, ColumnTypeEnum> getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(Map<String, ColumnTypeEnum> fieldType) {
        this.fieldType = fieldType;
    }

    public List<Object[]> getDatas() {
        return this.datas;
    }

    public void setDatas(List<Object[]> datas) {
        this.datas = datas;
    }
}

