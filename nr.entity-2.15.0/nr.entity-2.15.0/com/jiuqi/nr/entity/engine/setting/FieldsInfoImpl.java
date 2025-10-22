/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.entity.engine.setting;

import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.HashMap;
import java.util.Locale;

public class FieldsInfoImpl
implements IFieldsInfo {
    private HashMap<String, ColumnModelType> fieldTypes = new HashMap(16);
    private HashMap<String, IEntityAttribute> fieldDefines = new HashMap(16);
    private HashMap<Integer, IEntityAttribute> indexMap = new HashMap(16);
    private int cursor = 0;

    @Override
    public int getFieldCount() {
        return this.fieldTypes.size();
    }

    @Override
    public IEntityAttribute getFieldByIndex(int idx) {
        return this.indexMap.get(idx);
    }

    @Override
    public ColumnModelType getDataType(String code) {
        return this.fieldTypes.get(code.toLowerCase(Locale.ROOT));
    }

    @Override
    public IEntityAttribute getFieldDefine(String code) {
        return this.fieldDefines.get(code.toLowerCase(Locale.ROOT));
    }

    @Override
    public void setupField(String code, IEntityAttribute fieldDefine) {
        this.fieldTypes.put(code.toLowerCase(Locale.ROOT), fieldDefine.getColumnType());
        this.fieldDefines.put(code.toLowerCase(Locale.ROOT), fieldDefine);
        this.indexMap.put(this.cursor, fieldDefine);
        ++this.cursor;
    }

    @Override
    public void setupField(String code, ColumnModelType fieldType) {
        this.fieldTypes.put(code.toLowerCase(Locale.ROOT), fieldType);
    }

    @Override
    public void reset() {
        this.fieldDefines.clear();
        this.fieldTypes.clear();
    }

    public HashMap<String, IEntityAttribute> getFieldsMap() {
        HashMap<String, IEntityAttribute> fieldsMap = new HashMap<String, IEntityAttribute>(this.fieldDefines.size());
        for (String code : this.fieldDefines.keySet()) {
            if (code == null) continue;
            String fieldName = this.fieldDefines.get(code).getName().toUpperCase();
            fieldsMap.put(fieldName, this.fieldDefines.get(code));
        }
        return fieldsMap;
    }
}

