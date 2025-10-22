/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 */
package com.jiuqi.nr.bpm.repair.db.utils;

import com.jiuqi.bi.database.metadata.LogicField;
import java.util.List;

public interface DBTableEntity {
    public boolean isExist();

    public String getTableName();

    public List<LogicField> getLogicFields();

    public List<LogicField> getPrimaryFields();

    public List<LogicField> getAllLogicFields();

    public static LogicField newLogicField(String fieldName, int dataType, int size) {
        LogicField field = new LogicField();
        field.setFieldName(fieldName);
        field.setSize(size);
        field.setDataType(dataType);
        return field;
    }
}

