/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BinaryData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.DateData;
import com.jiuqi.np.dataengine.data.DateTimeData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.GuidData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.data.ObjectData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.data.VoidData;

public class DataTypes {
    public static final int ERROR = -1;
    public static final int VOID = -2;
    public static final int BOOLEN = 1;
    public static final int DATETIME = 2;
    public static final int FLOAT = 3;
    public static final int INT = 4;
    public static final int DATE = 5;
    public static final int STRING = 6;
    public static final int GENERAL = 7;
    public static final int TIME = 8;
    public static final int CURRENCY = 10;
    public static final int OBJECT_ARRAY = 11;
    public static final int OBJECT = 0;
    public static final int OBJECT_GUID = 33;
    public static final int OBJECT_TEXT = 34;
    public static final int OBJECT_PICTURE = 35;
    public static final int OBJECT_ATTACHMENT = 36;
    public static final int OBJECT_BINARY = 37;
    public static final int OBJECT_LIST = 65;
    public static final int MAX = 128;
    private static final AbstractData[] NULL_VALUES = new AbstractData[]{ObjectData.NULL, BoolData.NULL, DateTimeData.NULL, FloatData.NULL, IntData.NULL, DateData.NULL, StringData.NULL};

    private DataTypes() {
    }

    public static String toString(int dataType) {
        switch (dataType) {
            case 1: {
                return "\u5e03\u5c14\u578b";
            }
            case 4: {
                return "\u6574\u6570\u578b";
            }
            case 3: {
                return "\u6570\u503c\u578b";
            }
            case 10: {
                return "\u5927\u7cbe\u5ea6\u578b";
            }
            case 6: {
                return "\u5b57\u7b26\u578b";
            }
            case 2: {
                return "\u65e5\u671f\u65f6\u95f4\u578b";
            }
            case 5: {
                return "\u65e5\u671f\u578b";
            }
            case 8: {
                return "\u65f6\u95f4\u578b";
            }
            case 33: {
                return "UUID\u578b";
            }
        }
        return "\u672a\u77e5\u7c7b\u578b";
    }

    public static AbstractData getNullValue(int dataType) {
        if (dataType >= -2 && dataType <= 6) {
            return NULL_VALUES[dataType];
        }
        switch (dataType) {
            case 10: {
                return CurrencyData.NULL;
            }
            case 8: {
                return DateTimeData.NULL;
            }
            case 33: {
                return GuidData.NULL;
            }
            case 34: 
            case 35: 
            case 36: {
                return StringData.NULL;
            }
            case 37: {
                return BinaryData.NULL;
            }
        }
        return VoidData.VOID;
    }

    public static boolean isNum(int dataType) {
        return dataType == 4 || dataType == 10 || dataType == 3;
    }
}

