/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.Base64
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.util.Base64;
import java.io.IOException;
import java.util.Calendar;

public class ParameterValueSerializer {
    public static String serialize(ParameterModel param, Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof MemoryDataSet) {
            MemoryDataSet mds = (MemoryDataSet)value;
            try {
                byte[] data = mds.save();
                return Base64.byteArrayToBase64((byte[])data);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        switch (param.getDataType()) {
            case BOOLEAN: {
                Boolean b = (Boolean)value;
                return b.toString();
            }
            case DATETIME: {
                Calendar c = (Calendar)value;
                return String.valueOf(c.getTimeInMillis());
            }
            case DOUBLE: {
                Double d = (Double)value;
                return String.valueOf(d);
            }
            case INTEGER: {
                Integer i = (Integer)value;
                return String.valueOf(i);
            }
            case STRING: {
                return value.toString();
            }
        }
        throw new RuntimeException("\u672a\u77e5\u7684\u6570\u636e\u7c7b\u578b");
    }

    public static Object deserialize(ParameterModel param, String value) {
        if (value == null) {
            return null;
        }
        if (param.getDataSourceModel() != null) {
            MemoryDataSet mds = new MemoryDataSet(ParameterColumnInfo.class);
            byte[] data = Base64.base64ToByteArray((String)value);
            try {
                mds.load(data);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            return mds;
        }
        switch (param.getDataType()) {
            case BOOLEAN: {
                return Boolean.parseBoolean(value);
            }
            case DATETIME: {
                long timestamp = Long.parseLong(value);
                Calendar c = Calendar.getInstance();
                c.clear();
                c.setTimeInMillis(timestamp);
                return c;
            }
            case DOUBLE: {
                return Double.parseDouble(value);
            }
            case INTEGER: {
                return Integer.parseInt(value);
            }
            case STRING: {
                return value.toString();
            }
        }
        throw new RuntimeException("\u672a\u77e5\u7684\u6570\u636e\u7c7b\u578b");
    }
}

