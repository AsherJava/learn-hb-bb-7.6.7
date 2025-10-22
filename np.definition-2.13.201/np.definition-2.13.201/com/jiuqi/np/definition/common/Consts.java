/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import java.util.Date;
import java.util.GregorianCalendar;

public class Consts {
    public static final String REST_PREFIX = "api/v1/definition/";
    public static final String DB_ORG_FIELD_NAME = "MDCODE";
    public static final String DB_PERIOD_FIELD_NAME = "DATATIME";
    public static final String BIZKEYORFER_CODE = "BIZKEYORDER";
    public static final String FLOATORFER_CODE = "FLOATORDER";
    public static final String DEFAULT_ENCODINGURI = "UTF-8";
    public static final String PREFIX_TB = "";
    public static final String PREVIX_FD = "";
    public static final String CHAR_UNDERLINE = "_";
    public static final String TEMPTABLE_PREFIX = "BAK_";
    public static final String DESIGNTIME_PREFIX = "DES_";
    public static final String SPLIT_CHAR = ";";
    public static final String FILECACHE_DIRECTORY = "JQTABLEDEFINE";
    public static final String FILECACHE_FILESUFFIX = ".jmdf";
    public static final String FILECACHE_ENTITYVIEW_FILESUFFIX = ".jmef";
    public static final String FILECACHE_FILENAME = "DEFINITION";
    public static final String FILECACHE_CUSTOM_FILESUFFIX = ".md";
    public static final String DEFAULT_ENTITY_CONNECT = "@";
    public static final int DBA_NONE = 0;
    public static final int DBA_DROP = 1;
    public static final int DBA_CREATE = 2;
    public static final int DBA_MODIFY = 3;
    public static final int DBA_MOVE = 4;
    public static final String ALL = "_ALL_";
    public static final String CACHEKEY_NP_DEFINITION = "CACHEKEY_NP_DEFINITION";
    public static final String CACHEKEY_NP_DEFINITION_ENTITYVIEW = "CACHEKEY_NP_DEFINITION_ENTITYVIEW";
    public static final String DATE_VERSION_BEGIN_FIELDNAME = "VALIDTIME";
    public static final String DATE_VERSION_END_FIELDNAME = "INVALIDTIME";
    public static final String DATE_STAMP_FIELDNAME = "UPDATETIME";
    public static final Date DATE_VERSION_INVALID_VALUE = new GregorianCalendar(1, 0, 1).getTime();
    public static final Date DATE_VERSION_MIN_VALUE = new GregorianCalendar(1970, 0, 1, 0, 0, 0).getTime();
    public static final Date DATE_VERSION_MAX_VALUE = new GregorianCalendar(2039, 0, 1, 0, 0, 0).getTime();
    public static final Date DATE_VERSION_FOR_ALL = new GregorianCalendar(9000, 0, 1).getTime();
    public static final Date ALL_VERSIONS_DATE = new GregorianCalendar(9000, 11, 31, 23, 59, 59).getTime();
    public static final String VERSION_CODE = "VERSION";
    public static final String VERSION_TITLE = "\u7248\u672c";
    public static final String DEFAULT_VERSION_ID = "00000000-0000-0000-0000-000000000000";
    public static final String PROCESS_CODE = "PROCESSKEY";
    public static final String PROCESS_TITLE = "\u6d41\u7a0b\u5b9a\u4e49KEY";
    public static final String DEFAULT_PROCESS_KEY = "00000000-0000-0000-0000-000000000000";
    public static final String PARENTS_FIELD = "PARENTS";
    public static final String LIST_SPLITER = ";";
    public static final int MAX_FIELDS_COUNT_IN_SINGLE_TABLE = 950;
    public static final String ENTITY_FIELD_KEY_DESCRIPTION = "DESCRIPTION";
    public static final String TABLE_TYPE_DEFAULT = "TABLE_DEFINE";
    public static final String TABLE_TYPE_OTHER = "OTHER_TABLE";
    public static final String FIELD_PROPERTY_DEFAULT = "NORMAL_FIELD";
    public static final String FIELD_PROPERTY_HB = "SYSTEM_FIELD";

    public static enum EnumField {
        ENUM_FIELD_KEY("ID", "\u552f\u4e00\u6807\u8bc6", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_RECORD_KEY, 50, "\u6570\u636e\u9879\u6761\u76ee\u552f\u4e00\u6807\u8bc6", false, ""),
        ENUM_FIELD_CODE("CODE", "\u4ee3\u7801", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_DICTIONARY_CODE, 100, "\u4ee3\u7801", false, ""),
        ENUM_FIELD_TITLE("NAME", "\u542b\u4e49", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_DICTIONARY_TITLE, 200, "", false, ""),
        ENUM_FIELD_PARENT("PARENTCODE", "\u7236\u8282\u70b9", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_DICTIONARY_PARENT, 50, "", true, ""),
        ENUM_FIELD_ORDER("ORDINAL", "\u6392\u5e8f\u5b57\u6bb5", FieldType.FIELD_TYPE_FLOAT, FieldValueType.FIELD_VALUE_UNIT_ORDER, 20, "\u6392\u5e8f\u5b57\u6bb5", false, "0");

        public String code;
        public String title;
        public FieldType type;
        public FieldValueType valueType;
        public String dbDefaultData;
        public int size;
        public String description;
        public boolean nullable;

        private EnumField(String code, String title, FieldType type, FieldValueType valueType, int size, String description, boolean nullable, String dbDefaultData) {
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

    public static enum EntityField {
        ENTITY_FIELD_KEY("ID", "\u4e3b\u952e", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_UNIT_KEY, 50, false),
        ENTITY_FIELD_CODE("CODE", "\u4ee3\u7801", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_UNIT_CODE, 100, false),
        ENTITY_FIELD_TITLE("NAME", "\u540d\u79f0", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_UNIT_NAME, 200, false),
        ENTITY_FIELD_PARENTKEY("PARENTCODE", "\u7236\u8282\u70b9", FieldType.FIELD_TYPE_STRING, FieldValueType.FIELD_VALUE_PARENT_UNIT, 50, true),
        ENTITY_FIELD_VALIDTIME("VALIDTIME", "\u751f\u6548\u65e5\u671f", FieldType.FIELD_TYPE_DATE, FieldValueType.FIELD_VALUE_DEFALUT, -1, false),
        ENTITY_FIELD_INVALIDTIME("INVALIDTIME", "\u5931\u6548\u65e5\u671f", FieldType.FIELD_TYPE_DATE, FieldValueType.FIELD_VALUE_DEFALUT, -1, false),
        ENTITY_FIELD_ORDINAL("ORDINAL", "\u6392\u5e8f\u5b57\u6bb5", FieldType.FIELD_TYPE_FLOAT, FieldValueType.FIELD_VALUE_UNIT_ORDER, 22, false);

        public String fieldKey;
        public String fieldTitle;
        public FieldType type;
        public FieldValueType valueType;
        public int size;
        public boolean nullable;

        private EntityField(String fieldKey, String fieldTitle, FieldType type, FieldValueType valueType, int size, boolean nullable) {
            this.fieldKey = fieldKey;
            this.fieldTitle = fieldTitle;
            this.type = type;
            this.valueType = valueType;
            this.size = size;
            this.nullable = nullable;
        }
    }
}

