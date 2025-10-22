/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.type.Convert
 */
package com.jiuqi.nr.entity.engine.common;

import com.jiuqi.np.sql.type.Convert;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataEngineConsts {
    public static byte[] toBytes(Object value) {
        if (value instanceof String) {
            String valueStr = (String)value;
            if (valueStr.length() == 32 || valueStr.length() == 36) {
                return Convert.toBytes((UUID)Convert.toUUID((String)valueStr));
            }
            return Convert.toBytes((Object)value);
        }
        return Convert.toBytes((Object)value);
    }

    public static List<String> getRecKeys(int size) {
        ArrayList<String> recKeys = new ArrayList<String>();
        for (int index = 0; index < size; ++index) {
            recKeys.add(UUID.randomUUID().toString());
        }
        return recKeys;
    }
}

