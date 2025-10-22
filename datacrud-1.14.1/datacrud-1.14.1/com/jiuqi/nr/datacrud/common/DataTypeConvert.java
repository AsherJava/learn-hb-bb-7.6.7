/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.datacrud.common;

import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class DataTypeConvert {
    public static int dataFieldType2DataType(int value) {
        if (DataFieldType.STRING.getValue() == value) {
            return 6;
        }
        if (DataFieldType.INTEGER.getValue() == value) {
            return 4;
        }
        if (DataFieldType.BOOLEAN.getValue() == value) {
            return 1;
        }
        if (DataFieldType.DATE.getValue() == value) {
            return 5;
        }
        if (DataFieldType.DATE_TIME.getValue() == value) {
            return 2;
        }
        if (DataFieldType.UUID.getValue() == value) {
            return 33;
        }
        if (DataFieldType.BIGDECIMAL.getValue() == value) {
            return 10;
        }
        if (DataFieldType.CLOB.getValue() == value) {
            return 34;
        }
        if (DataFieldType.PICTURE.getValue() == value) {
            return 35;
        }
        if (DataFieldType.FILE.getValue() == value) {
            return 36;
        }
        return -1;
    }

    public static int columnModelType2DataType(int value) {
        if (ColumnModelType.STRING.getValue() == value) {
            return 6;
        }
        if (ColumnModelType.INTEGER.getValue() == value) {
            return 4;
        }
        if (ColumnModelType.BOOLEAN.getValue() == value) {
            return 1;
        }
        if (ColumnModelType.DATETIME.getValue() == value) {
            return 5;
        }
        if (ColumnModelType.UUID.getValue() == value) {
            return 33;
        }
        if (ColumnModelType.BIGDECIMAL.getValue() == value) {
            return 10;
        }
        if (ColumnModelType.DOUBLE.getValue() == value) {
            return 3;
        }
        if (ColumnModelType.CLOB.getValue() == value) {
            return 34;
        }
        if (ColumnModelType.ATTACHMENT.getValue() == value) {
            return 36;
        }
        if (ColumnModelType.BLOB.getValue() == value) {
            return 37;
        }
        return -1;
    }

    public static DataFieldType dataType2DataFieldType(int abstractDataType) {
        switch (abstractDataType) {
            case 6: {
                return DataFieldType.STRING;
            }
            case 4: {
                return DataFieldType.INTEGER;
            }
            case 1: {
                return DataFieldType.BOOLEAN;
            }
            case 5: {
                return DataFieldType.DATE;
            }
            case 2: {
                return DataFieldType.DATE_TIME;
            }
            case 10: {
                return DataFieldType.BIGDECIMAL;
            }
            case 34: {
                return DataFieldType.CLOB;
            }
            case 35: {
                return DataFieldType.PICTURE;
            }
            case 36: {
                return DataFieldType.FILE;
            }
        }
        return null;
    }

    public static DataFieldType convertDataType(ColumnModelType columnType) {
        if (columnType == null) {
            return null;
        }
        switch (columnType) {
            case STRING: {
                return DataFieldType.STRING;
            }
            case UUID: {
                return DataFieldType.UUID;
            }
            case DATETIME: {
                return DataFieldType.DATE;
            }
            case BOOLEAN: {
                return DataFieldType.BOOLEAN;
            }
            case INTEGER: {
                return DataFieldType.INTEGER;
            }
            case BIGDECIMAL: 
            case DOUBLE: {
                return DataFieldType.BIGDECIMAL;
            }
            case CLOB: {
                return DataFieldType.CLOB;
            }
            case ATTACHMENT: {
                return DataFieldType.FILE;
            }
        }
        return null;
    }

    public static int dataFieldType2TdsType(int value) {
        if (DataFieldType.STRING.getValue() == value) {
            return 6;
        }
        if (DataFieldType.INTEGER.getValue() == value) {
            return 5;
        }
        if (DataFieldType.BOOLEAN.getValue() == value) {
            return 1;
        }
        if (DataFieldType.DATE.getValue() == value) {
            return 2;
        }
        if (DataFieldType.DATE_TIME.getValue() == value) {
            return 2;
        }
        if (DataFieldType.BIGDECIMAL.getValue() == value) {
            return 10;
        }
        if (DataFieldType.CLOB.getValue() == value) {
            return 6;
        }
        if (DataFieldType.PICTURE.getValue() == value) {
            return 6;
        }
        if (DataFieldType.FILE.getValue() == value) {
            return 6;
        }
        throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301\u7c7b\u578b\uff1a" + value);
    }
}

