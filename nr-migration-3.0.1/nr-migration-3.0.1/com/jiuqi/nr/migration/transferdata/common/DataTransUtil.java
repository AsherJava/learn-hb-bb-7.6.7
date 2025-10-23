/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 */
package com.jiuqi.nr.migration.transferdata.common;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import java.text.SimpleDateFormat;

public class DataTransUtil {
    public static final int NUMERIC = 1;
    public static final int STRING = 2;
    public static final int INTEGER = 3;
    public static final int BOOLEAN = 4;
    public static final int DATE = 5;
    public static final int DATETIME = 6;
    public static final int GUID = 7;
    public static final int REMARK = 8;
    public static final int BINARY = 9;
    public static final int VARBINARY = 10;
    public static final int UNKNOWN = -1;

    public static String getFieldValue(IDataValue dataValue) {
        if (dataValue.getAsNull()) {
            return null;
        }
        switch (dataValue.getMetaData().getDataType()) {
            case 1: {
                return String.valueOf(dataValue.getAsBool());
            }
            case 2: 
            case 5: 
            case 8: {
                return DataTransUtil.getTimeDateValueIDataValue(dataValue);
            }
            case 3: {
                return String.valueOf(dataValue.getAsFloat());
            }
            case 4: {
                return String.valueOf(dataValue.getAsInt());
            }
            case 10: {
                return String.valueOf(dataValue.getAsCurrency());
            }
        }
        return dataValue.getAsString();
    }

    private static String getTimeDateValueIDataValue(IDataValue timeData) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if ("null".equals(String.valueOf(timeData.getAsObject()))) {
                return "null";
            }
            return formatter.format(timeData.getAsDate());
        }
        catch (IllegalArgumentException e) {
            System.out.println("\u65f6\u95f4\u683c\u5f0f\u9519\u8bef: " + e.getMessage());
            return "\u65f6\u95f4\u683c\u5f0f\u9519\u8bef";
        }
    }

    private static String getTimeDateValue(AbstractData timeData) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if ("null".equals(String.valueOf(timeData.getAsObject()))) {
                return "null";
            }
            return formatter.format(timeData.getAsDate());
        }
        catch (IllegalArgumentException e) {
            System.out.println("\u65f6\u95f4\u683c\u5f0f\u9519\u8bef: " + e.getMessage());
            return "\u65f6\u95f4\u683c\u5f0f\u9519\u8bef";
        }
    }

    public static String getFieldValue(AbstractData dataValue) {
        try {
            switch (dataValue.dataType) {
                case 1: {
                    return String.valueOf(dataValue.getAsBool());
                }
                case 2: 
                case 5: 
                case 8: {
                    return DataTransUtil.getTimeDateValue(dataValue);
                }
                case 3: {
                    return String.valueOf(dataValue.getAsFloat());
                }
                case 4: {
                    return String.valueOf(dataValue.getAsInt());
                }
                case 10: {
                    return String.valueOf(dataValue.getAsCurrency());
                }
            }
            return dataValue.getAsString();
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static int getJqrZbType(int nrDataType) {
        switch (nrDataType) {
            case 1: {
                return 4;
            }
            case 2: 
            case 8: {
                return 6;
            }
            case 3: 
            case 10: {
                return 1;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 5;
            }
            case 6: {
                return 2;
            }
            case 33: {
                return 7;
            }
            case 36: {
                return 8;
            }
        }
        return -1;
    }

    public static String getCrudExceptionMsg(int code, String message) {
        switch (code) {
            case 4000: {
                return "\u6ca1\u6709\u53ef\u64cd\u4f5c\u6570\u636e\u7684\u6743\u9650";
            }
            case 4001: {
                return "\u6307\u6807\u6743\u9650\u5224\u65ad\u51fa\u9519";
            }
            case 4002: {
                return "\u6307\u6807\u65e0\u6743\u9650\u5f3a\u5236\u53d6\u6570\u9519\u8bef";
            }
            case 4101: {
                return "\u6570\u636e\u5f15\u64ce\u53d6\u6570\u9519\u8bef";
            }
            case 4111: {
                return "\u6570\u636e\u5f15\u64ce\u6267\u884c\u6e05\u9664\u9519\u8bef";
            }
        }
        return message;
    }
}

