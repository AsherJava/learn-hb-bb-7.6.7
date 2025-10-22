/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.snapshot.utils;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;

public class SnapshotDataTypeUtil {
    public static int dataFieldType2DataType(int value, DataFieldGatherType dataFieldGatherType) {
        if (null == dataFieldGatherType || dataFieldGatherType.equals((Object)DataFieldGatherType.NONE)) {
            return 6;
        }
        if (dataFieldGatherType.equals((Object)DataFieldGatherType.SUM) || dataFieldGatherType.equals((Object)DataFieldGatherType.MIN) || dataFieldGatherType.equals((Object)DataFieldGatherType.MAX)) {
            return SnapshotDataTypeUtil.dataFieldType2DataType(value);
        }
        if (dataFieldGatherType.equals((Object)DataFieldGatherType.COUNT) || dataFieldGatherType.equals((Object)DataFieldGatherType.DISTINCT_COUNT)) {
            return 4;
        }
        if (dataFieldGatherType.equals((Object)DataFieldGatherType.AVERAGE)) {
            return 3;
        }
        return -1;
    }

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
}

