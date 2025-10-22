/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 */
package com.jiuqi.gcreport.inputdata.gcoppunit;

import com.jiuqi.np.definition.common.FieldType;

public enum DataInputTypeEnum {
    STRING("\u6587\u672c\u7c7b\u63a7\u4ef6"),
    TEXT("\u6587\u672c\u57df\u7c7b\u63a7\u4ef6"),
    DATE("\u65e5\u671f\u7c7b\u63a7\u4ef6"),
    NUMBER("\u6570\u503c\u7c7b\u63a7\u4ef6"),
    ENTITY("\u4e3b\u4f53\u9009\u62e9\u7c7b\u63a7\u4ef6"),
    FILE("\u6587\u4ef6\u4e0a\u4f20\u7c7b\u63a7\u4ef6"),
    RADIO("\u5355\u9009\u7c7b\u63a7\u4ef6"),
    CHECKBOX("\u591a\u9009\u7c7b\u63a7\u4ef6");


    private DataInputTypeEnum(String title) {
    }

    public static DataInputTypeEnum typeOf(FieldType type) {
        switch (type) {
            case FIELD_TYPE_DATE: 
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_TIME: 
            case FIELD_TYPE_TIME_STAMP: {
                return DATE;
            }
            case FIELD_TYPE_DECIMAL: 
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_INTEGER: {
                return NUMBER;
            }
            case FIELD_TYPE_LOGIC: {
                return RADIO;
            }
            case FIELD_TYPE_BINARY: 
            case FIELD_TYPE_OBJECT_ARRAY: 
            case FIELD_TYPE_PICTURE: 
            case FIELD_TYPE_QRCODE: {
                return FILE;
            }
            case FIELD_TYPE_TEXT: {
                return TEXT;
            }
        }
        return STRING;
    }
}

