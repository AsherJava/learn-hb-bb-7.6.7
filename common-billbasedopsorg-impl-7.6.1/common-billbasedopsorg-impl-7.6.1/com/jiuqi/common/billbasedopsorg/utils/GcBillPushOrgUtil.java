/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 */
package com.jiuqi.common.billbasedopsorg.utils;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GcBillPushOrgUtil {
    public static List<DataModelColumn> mergeDataModel(DataModelDO origalDataModel, DataModelDO dataModelDO) {
        if (origalDataModel == null || origalDataModel.getColumns() == null) {
            return dataModelDO == null ? null : dataModelDO.getColumns();
        }
        if (dataModelDO == null || dataModelDO.getColumns() == null) {
            return origalDataModel == null ? null : origalDataModel.getColumns();
        }
        LinkedHashMap<String, DataModelColumn> columnMap = new LinkedHashMap<String, DataModelColumn>();
        String columnName = null;
        for (DataModelColumn dataModelColumn : origalDataModel.getColumns()) {
            columnName = dataModelColumn.getColumnName().toUpperCase().trim();
            dataModelColumn.setColumnName(columnName);
            columnMap.put(columnName, dataModelColumn);
        }
        for (DataModelColumn column : dataModelDO.getColumns()) {
            columnName = column.getColumnName().toUpperCase().trim();
            column.setColumnName(columnName);
            if (!columnMap.containsKey(columnName)) {
                columnMap.put(columnName, column);
                continue;
            }
            ((DataModelColumn)columnMap.get(columnName)).columnTitle(column.getColumnTitle()).lengths(column.getLengths());
        }
        return new ArrayList<DataModelColumn>(columnMap.values());
    }
}

