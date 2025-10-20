/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 */
package com.jiuqi.nr.designer.common;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;

public enum DefaultTableField {
    TB_FD_ID("ID", "\u552f\u4e00\u6807\u8bc6", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_RECORD_KEY, 100, "\u6570\u636e\u9879\u6761\u76ee\u552f\u4e00\u6807\u8bc6", true),
    TB_FD_CODE("CODE", "\u6807\u8bc6", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_DICTIONARY_CODE, 100, "\u6807\u8bc6", true),
    TB_FD_TITLE("TITLE", "\u6807\u9898", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_DICTIONARY_TITLE, 30, "\u6807\u9898", true),
    TB_FD_PARENTID("PARENTID", "\u7236\u8282\u70b9", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_RECORD_KEY, 100, "\u7236\u8282\u70b9", true),
    TB_FD_BASEUNIT("BASEUNIT", "\u662f\u5426\u662f\u57fa\u51c6\u5355\u4f4d", FieldType.FIELD_TYPE_INTEGER, FieldValueType.FIELD_VALUE_DEFALUT, 10, "\u662f\u5426\u662f\u57fa\u51c6\u5355\u4f4d", true),
    TB_FD_RATIO("RATIO", "\u5355\u4f4d\u6bd4\u7387", FieldType.FIELD_TYPE_DECIMAL, FieldValueType.FIELD_VALUE_DEFALUT, 37, "\u5355\u4f4d\u6bd4\u7387", true);

    private String code;
    private String title;
    private FieldType type;
    private FieldValueType valueType;
    private int size;
    private String description;
    private boolean nullable;

    private DefaultTableField(String code, String title, FieldType type, FieldValueType valueType, int size, String description, boolean nullable) {
        this.code = code;
        this.title = title;
        this.type = type;
        this.valueType = valueType;
        this.size = size;
        this.description = description;
        this.nullable = nullable;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public FieldType getType() {
        return this.type;
    }

    public FieldValueType getValueType() {
        return this.valueType;
    }

    public int getSize() {
        return this.size;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isNullable() {
        return this.nullable;
    }
}

