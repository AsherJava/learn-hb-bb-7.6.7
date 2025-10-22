/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.setting;

import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.lang.reflect.Array;
import java.util.HashMap;

public class FieldsInfoImpl
implements IFieldsInfo {
    private FieldType[] fieldTypes;
    private FieldDefine[] fieldDefines;
    private final HashMap<String, Integer> fieldsSearch;

    public FieldsInfoImpl(int fieldCount) {
        this.fieldTypes = new FieldType[fieldCount];
        this.fieldDefines = new FieldDefine[fieldCount];
        this.fieldsSearch = new HashMap();
    }

    @Override
    public int getFieldCount() {
        return this.fieldTypes.length;
    }

    @Override
    public FieldType getDataType(int fieldIndex) {
        return this.fieldTypes[fieldIndex];
    }

    @Override
    public FieldDefine getFieldDefine(int fieldIndex) {
        return this.fieldDefines[fieldIndex];
    }

    @Override
    public int indexOf(FieldDefine fieldDefine) {
        Integer fieldIndex = this.fieldsSearch.get(fieldDefine.getKey());
        if (fieldIndex == null) {
            return -1;
        }
        return fieldIndex;
    }

    @Override
    public void setupField(int fieldIndex, FieldDefine fieldDefine) {
        this.fieldTypes[fieldIndex] = fieldDefine.getType();
        this.fieldDefines[fieldIndex] = fieldDefine;
        if (!this.fieldsSearch.containsKey(fieldDefine.getKey())) {
            this.fieldsSearch.put(fieldDefine.getKey(), fieldIndex);
        }
    }

    @Override
    public void setupField(int fieldIndex, FieldType fieldType) {
        this.fieldTypes[fieldIndex] = fieldType;
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
        this.fieldTypes = (FieldType[])newArray;
        elementType = this.fieldDefines.getClass().getComponentType();
        newArray = Array.newInstance(elementType, newSize);
        System.arraycopy(this.fieldDefines, 0, newArray, 0, oldSize);
        this.fieldDefines = (FieldDefine[])newArray;
    }

    @Override
    public int indexOf(String fieldKey) {
        Integer fieldIndex = this.fieldsSearch.get(fieldKey);
        if (fieldIndex == null) {
            return -1;
        }
        return fieldIndex;
    }

    public HashMap<String, FieldDefine> getFieldsMap() {
        HashMap<String, FieldDefine> fieldsMap = new HashMap<String, FieldDefine>();
        for (FieldDefine fieldDefine : this.fieldDefines) {
            if (fieldDefine == null) continue;
            String fieldName = fieldDefine.getCode().toUpperCase();
            fieldsMap.put(fieldName, fieldDefine);
        }
        return fieldsMap;
    }
}

