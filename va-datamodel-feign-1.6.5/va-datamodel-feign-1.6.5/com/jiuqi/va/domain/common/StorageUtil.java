/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplateDTO;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import com.jiuqi.va.feign.client.DataModelTemplateClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class StorageUtil {
    private static DataModelTemplateClient client = null;

    public static List<DataModelColumn> mergeDataModel(DataModelDO oldDataModel, DataModelDO newDataModel) {
        if (oldDataModel == null || oldDataModel.getColumns() == null) {
            return newDataModel == null ? null : newDataModel.getColumns();
        }
        if (newDataModel == null || newDataModel.getColumns() == null) {
            return oldDataModel.getColumns();
        }
        LinkedHashMap<String, DataModelColumn> columnMap = new LinkedHashMap<String, DataModelColumn>();
        String columnName = null;
        for (DataModelColumn dataModelColumn : oldDataModel.getColumns()) {
            columnName = dataModelColumn.getColumnName();
            dataModelColumn.setColumnName(columnName);
            columnMap.put(columnName, dataModelColumn);
        }
        for (DataModelColumn column : newDataModel.getColumns()) {
            columnName = column.getColumnName();
            column.setColumnName(columnName);
            if (!columnMap.containsKey(columnName)) {
                columnMap.put(columnName, column);
                continue;
            }
            ((DataModelColumn)columnMap.get(columnName)).columnTitle(column.getColumnTitle()).columnType(column.getColumnType()).lengths(column.getLengths()).nullable(column.getNullable()).defaultVal(column.getDefaultVal());
            if (DataModelType.ColumnAttr.SYSTEM == column.getColumnAttr()) continue;
            ((DataModelColumn)columnMap.get(columnName)).mappingType(column.getMappingType()).mapping(column.getMapping());
        }
        return new ArrayList<DataModelColumn>(columnMap.values());
    }

    public static List<DataModelColumn> getTemplateFields(String templateName) {
        DataModelTemplateEntity template = StorageUtil.getDataModelTemplate(templateName);
        return template.getTemplateFields();
    }

    public static DataModelTemplateEntity getDataModelTemplate(String templateName) {
        return StorageUtil.getDataModelTemplate(templateName, null);
    }

    public static DataModelTemplateEntity getDataModelTemplate(String templateName, String tableName) {
        DataModelTemplateDTO param = new DataModelTemplateDTO(templateName, tableName);
        return StorageUtil.getDataModelTemplate(param);
    }

    public static DataModelTemplateEntity getDataModelTemplate(DataModelTemplateDTO param) {
        if (client == null) {
            client = (DataModelTemplateClient)ApplicationContextRegister.getBean(DataModelTemplateClient.class);
        }
        return client.getDataModelTemplate(param);
    }
}

