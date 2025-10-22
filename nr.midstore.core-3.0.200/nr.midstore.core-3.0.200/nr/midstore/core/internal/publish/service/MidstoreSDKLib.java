/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package nr.midstore.core.internal.publish.service;

import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.va.domain.datamodel.DataModelType;

public class MidstoreSDKLib {
    public static DEDataType getDEDataType(DataFieldType fieldType) {
        DEDataType aType = DEDataType.STRING;
        if (fieldType == DataFieldType.STRING) {
            aType = DEDataType.STRING;
        } else if (fieldType == DataFieldType.BIGDECIMAL) {
            aType = DEDataType.FLOAT;
        } else if (fieldType == DataFieldType.BOOLEAN) {
            aType = DEDataType.BOOLEAN;
        } else if (fieldType == DataFieldType.CLOB) {
            aType = DEDataType.CLOB;
        } else if (fieldType == DataFieldType.DATE) {
            aType = DEDataType.DATE;
        } else if (fieldType == DataFieldType.DATE_TIME) {
            aType = DEDataType.DATE;
        } else if (fieldType == DataFieldType.FILE) {
            aType = DEDataType.FILE;
        } else if (fieldType == DataFieldType.INTEGER) {
            aType = DEDataType.INTEGER;
        } else if (fieldType == DataFieldType.PICTURE) {
            aType = DEDataType.FILE;
        } else if (fieldType == DataFieldType.UUID) {
            aType = DEDataType.STRING;
        }
        return aType;
    }

    public static DEDataType getDEDataTypeByOrg(int fieldType) {
        DEDataType aType = DEDataType.STRING;
        if (fieldType == 2) {
            aType = DEDataType.STRING;
        } else if (fieldType == 4) {
            aType = DEDataType.FLOAT;
        } else if (fieldType == 8) {
            aType = DEDataType.BOOLEAN;
        } else if (fieldType == 7) {
            aType = DEDataType.CLOB;
        } else if (fieldType == 5) {
            aType = DEDataType.DATE;
        } else if (fieldType == 5) {
            aType = DEDataType.DATE;
        } else if (fieldType == 3) {
            aType = DEDataType.INTEGER;
        } else if (fieldType == 1) {
            aType = DEDataType.STRING;
        }
        return aType;
    }

    public static DEDataType getDEDataTypeByColumn(DataModelType.ColumnType fieldType) {
        DEDataType aType = DEDataType.STRING;
        if (fieldType == DataModelType.ColumnType.NVARCHAR) {
            aType = DEDataType.STRING;
        } else if (fieldType == DataModelType.ColumnType.NUMERIC) {
            aType = DEDataType.FLOAT;
        } else if (fieldType == DataModelType.ColumnType.CLOB) {
            aType = DEDataType.CLOB;
        } else if (fieldType == DataModelType.ColumnType.DATE) {
            aType = DEDataType.DATE;
        } else if (fieldType == DataModelType.ColumnType.DATE) {
            aType = DEDataType.DATE;
        } else if (fieldType == DataModelType.ColumnType.INTEGER) {
            aType = DEDataType.INTEGER;
        } else if (fieldType == DataModelType.ColumnType.UUID) {
            aType = DEDataType.STRING;
        }
        return aType;
    }

    public static DataFieldType getDataTypeByColumn(DataModelType.ColumnType fieldType) {
        DataFieldType aType = DataFieldType.STRING;
        if (fieldType == DataModelType.ColumnType.NVARCHAR) {
            aType = DataFieldType.STRING;
        } else if (fieldType == DataModelType.ColumnType.NUMERIC) {
            aType = DataFieldType.BIGDECIMAL;
        } else if (fieldType == DataModelType.ColumnType.CLOB) {
            aType = DataFieldType.CLOB;
        } else if (fieldType == DataModelType.ColumnType.DATE) {
            aType = DataFieldType.DATE;
        } else if (fieldType == DataModelType.ColumnType.DATE) {
            aType = DataFieldType.DATE;
        } else if (fieldType == DataModelType.ColumnType.INTEGER) {
            aType = DataFieldType.INTEGER;
        } else if (fieldType == DataModelType.ColumnType.UUID) {
            aType = DataFieldType.STRING;
        }
        return aType;
    }

    public static DataFieldType getDataTypeByOrg(int fieldType) {
        DataFieldType aType = DataFieldType.STRING;
        if (fieldType == 2) {
            aType = DataFieldType.STRING;
        } else if (fieldType == 4) {
            aType = DataFieldType.BIGDECIMAL;
        } else if (fieldType == 8) {
            aType = DataFieldType.BOOLEAN;
        } else if (fieldType == 7) {
            aType = DataFieldType.CLOB;
        } else if (fieldType == 5) {
            aType = DataFieldType.DATE;
        } else if (fieldType == 5) {
            aType = DataFieldType.DATE;
        } else if (fieldType == 3) {
            aType = DataFieldType.INTEGER;
        } else if (fieldType == 1) {
            aType = DataFieldType.STRING;
        }
        return aType;
    }
}

