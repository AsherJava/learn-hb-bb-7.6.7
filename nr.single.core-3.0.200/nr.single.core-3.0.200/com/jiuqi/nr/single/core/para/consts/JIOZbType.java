/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import com.jiuqi.nr.single.core.para.consts.ZBDataType;

public class JIOZbType {
    public static final int DOSUM_BITMASK = 1;
    public static final int JEDW_DATA_BITMASK = 2;

    public static ZBDataType ChangeJIOType(String jioType) {
        if (jioType.equals("C")) {
            return ZBDataType.STRING;
        }
        if (jioType.equals("N")) {
            return ZBDataType.NUMERIC;
        }
        if (jioType.equals("D")) {
            return ZBDataType.DATE;
        }
        if (jioType.equals("R")) {
            return ZBDataType.REMARK;
        }
        if (jioType.equals("B")) {
            return ZBDataType.ATTATCHMENT;
        }
        if (jioType.equals("L")) {
            return ZBDataType.BOOLEAN;
        }
        if (jioType.equals("G")) {
            return ZBDataType.PICTURE;
        }
        if (jioType.equals("I")) {
            return ZBDataType.INTEGER;
        }
        if (jioType.equals("O")) {
            return ZBDataType.ATTATCHMENT;
        }
        return ZBDataType.UNKNOWN;
    }

    public static String JIOType2String(ZBDataType type) {
        if (ZBDataType.STRING == type) {
            return "C";
        }
        if (ZBDataType.NUMERIC == type) {
            return "N";
        }
        if (ZBDataType.DATE == type) {
            return "D";
        }
        if (ZBDataType.REMARK == type) {
            return "R";
        }
        if (ZBDataType.ATTATCHMENT == type) {
            return "B";
        }
        if (ZBDataType.DOUBLE == type) {
            return "B";
        }
        if (ZBDataType.BOOLEAN == type) {
            return "L";
        }
        if (ZBDataType.PICTURE == type) {
            return "G";
        }
        if (ZBDataType.INTEGER == type) {
            return "I";
        }
        return "";
    }
}

