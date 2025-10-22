/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicIndex
 *  com.jiuqi.bi.database.metadata.LogicIndexField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.io.tz.bean;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.io.tz.bean.TempIndex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class BaseTempTableInfo {
    public void parseFieldType(DataField tableDimField, LogicField dimField) {
        switch (tableDimField.getDataFieldType()) {
            case DATE_TIME: {
                dimField.setDataType(2);
                dimField.setDataTypeName("timestamp");
                break;
            }
            case BIGDECIMAL: {
                dimField.setDataType(3);
                if (tableDimField.getPrecision() != null) {
                    dimField.setPrecision(tableDimField.getPrecision().intValue());
                }
                if (tableDimField.getDecimal() == null) break;
                dimField.setScale(tableDimField.getDecimal().intValue());
                break;
            }
            case STRING: 
            case PICTURE: 
            case FILE: {
                dimField.setDataType(6);
                if (tableDimField.getPrecision() != null) {
                    dimField.setSize(tableDimField.getPrecision().intValue());
                    dimField.setRawType(-9);
                    break;
                }
                dimField.setSize(255);
                break;
            }
            default: {
                if (tableDimField.getPrecision() != null) {
                    dimField.setSize(tableDimField.getPrecision().intValue());
                } else {
                    dimField.setSize(255);
                }
                dimField.setDataType(tableDimField.getDataFieldType().getValue());
            }
        }
    }

    public List<LogicIndex> getIndex(LogicTable logicTable, Set<TempIndex> indexFieldNames) {
        if (CollectionUtils.isEmpty(indexFieldNames)) {
            return Collections.emptyList();
        }
        ArrayList<LogicIndex> allIndex = new ArrayList<LogicIndex>();
        int num = 0;
        for (TempIndex tempIndex : indexFieldNames) {
            ++num;
            List<String> filedNames = tempIndex.getFiledNames();
            if (CollectionUtils.isEmpty(filedNames)) continue;
            LogicIndex index = new LogicIndex();
            index.setTableName(logicTable.getName());
            index.setIndexName(logicTable.getName() + "_" + num);
            ArrayList<LogicIndexField> indexFields = new ArrayList<LogicIndexField>();
            for (String name : filedNames) {
                LogicIndexField indexField = new LogicIndexField();
                indexField.setFieldName(name);
                indexFields.add(indexField);
            }
            index.setIndexFields(indexFields);
            index.setUnique(tempIndex.isUnique());
            allIndex.add(index);
        }
        return allIndex;
    }
}

