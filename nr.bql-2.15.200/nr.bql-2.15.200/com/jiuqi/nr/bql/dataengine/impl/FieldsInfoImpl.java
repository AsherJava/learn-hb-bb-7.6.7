/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.nr.bql.dataengine.IFieldsInfo;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.lang.reflect.Array;
import java.util.HashMap;

public class FieldsInfoImpl
implements IFieldsInfo {
    private int[] fieldTypes;
    private ColumnModelDefine[] fieldDefines;
    private final HashMap<String, Integer> fieldsSearch;

    public FieldsInfoImpl(int fieldCount) {
        this.fieldTypes = new int[fieldCount];
        this.fieldDefines = new ColumnModelDefine[fieldCount];
        this.fieldsSearch = new HashMap();
    }

    @Override
    public int getFieldCount() {
        return this.fieldTypes.length;
    }

    @Override
    public int getDataType(int fieldIndex) {
        return this.fieldTypes[fieldIndex];
    }

    @Override
    public ColumnModelDefine getFieldDefine(int fieldIndex) {
        return this.fieldDefines[fieldIndex];
    }

    @Override
    public int indexOf(ColumnModelDefine fieldDefine) {
        Integer fieldIndex = this.fieldsSearch.get(fieldDefine.getID());
        if (fieldIndex == null) {
            return -1;
        }
        return fieldIndex;
    }

    @Override
    public void setupField(int fieldIndex, ColumnModelDefine fieldDefine) {
        this.fieldTypes[fieldIndex] = DataTypesConvert.fieldTypeToDataType((ColumnModelType)fieldDefine.getColumnType());
        this.fieldDefines[fieldIndex] = fieldDefine;
        if (!this.fieldsSearch.containsKey(fieldDefine.getID())) {
            this.fieldsSearch.put(fieldDefine.getID(), fieldIndex);
        }
    }

    @Override
    public void setupField(int fieldIndex, int dataType) {
        this.fieldTypes[fieldIndex] = dataType;
        this.fieldDefines[fieldIndex] = null;
    }

    @Override
    public void reset() {
        this.fieldsSearch.clear();
    }

    @Override
    public void appendFields(int appendCount) {
        int oldSize = this.fieldTypes.length;
        int newSize = appendCount + oldSize;
        Class<?> elementType = this.fieldTypes.getClass().getComponentType();
        Object newArray = Array.newInstance(elementType, newSize);
        System.arraycopy(this.fieldTypes, 0, newArray, 0, oldSize);
        this.fieldTypes = (int[])newArray;
        elementType = this.fieldDefines.getClass().getComponentType();
        newArray = Array.newInstance(elementType, newSize);
        System.arraycopy(this.fieldDefines, 0, newArray, 0, oldSize);
        this.fieldDefines = (ColumnModelDefine[])newArray;
    }

    @Override
    public int indexOf(String fieldKey) {
        Integer fieldIndex = this.fieldsSearch.get(fieldKey);
        if (fieldIndex == null) {
            return -1;
        }
        return fieldIndex;
    }

    public HashMap<String, ColumnModelDefine> getFieldsMap() {
        HashMap<String, ColumnModelDefine> fieldsMap = new HashMap<String, ColumnModelDefine>();
        for (ColumnModelDefine fieldDefine : this.fieldDefines) {
            if (fieldDefine == null) continue;
            String fieldName = fieldDefine.getCode().toUpperCase();
            fieldsMap.put(fieldName, fieldDefine);
        }
        return fieldsMap;
    }
}

