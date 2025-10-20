/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableMeta;

public abstract class AbstractTempTableMeta
implements ITempTableMeta {
    protected LogicField createStringField(String fieldName, int size) {
        LogicField field = new LogicField();
        field.setDataType(6);
        field.setFieldName(fieldName);
        field.setSize(size);
        field.setRawType(-9);
        return field;
    }

    protected LogicField createIntegerField(String fieldName, int precision) {
        LogicField field = new LogicField();
        field.setDataType(5);
        field.setFieldName(fieldName);
        field.setPrecision(precision);
        return field;
    }

    protected LogicField createNumberField(String fieldName, int precision, int scale) {
        LogicField field = new LogicField();
        field.setDataType(3);
        field.setFieldName(fieldName);
        field.setPrecision(precision);
        field.setScale(scale);
        return field;
    }

    protected LogicField createBooleanField(String fieldName) {
        LogicField field = new LogicField();
        field.setDataType(3);
        field.setPrecision(1);
        field.setScale(0);
        field.setSize(1);
        return field;
    }

    protected LogicField createDateTimeField(String fieldName) {
        LogicField field = new LogicField();
        field.setDataTypeName("timestamp");
        field.setDataType(2);
        return field;
    }
}

