/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 */
package com.jiuqi.nr.designer.bean;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;

public class EnumConstants {
    public static final Integer UUID_LENGTH = 36;
    public static final String DIC_SYSMBOL_SEMICOLON = ";";
    public static final String DIC_SYSMBOL_VOID = "VOID";

    public static enum HistoryField {
        ENUM_FIELD_TITLE("TITLE", "\u542b\u4e49", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_DICTIONARY_TITLE, 200, "", false, ""),
        ENUM_FIELD_PARENT("PARENTID", "\u7236\u8282\u70b9", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_DICTIONARY_PARENT, 50, "", true, "");

        public String code;
        public String title;
        public FieldType type;
        public FieldValueType valueType;
        public String dbDefaultData;
        public int size;
        public String description;
        public boolean nullable;

        private HistoryField(String code, String title, FieldType type, FieldValueType valueType, int size, String description, boolean nullable, String dbDefaultData) {
            this.code = code;
            this.title = title;
            this.type = type;
            this.valueType = valueType;
            this.size = size;
            this.description = description;
            this.nullable = nullable;
            this.dbDefaultData = dbDefaultData;
        }
    }
}

