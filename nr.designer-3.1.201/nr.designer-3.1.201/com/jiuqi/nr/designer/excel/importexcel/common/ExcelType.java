/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

public enum ExcelType {
    XLSHXXF(0),
    XLSXXSSF(1);

    private final int value;
    private static ExcelType[] TYPES;

    private ExcelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ExcelType fromType(int type) {
        return TYPES[type];
    }

    static {
        TYPES = new ExcelType[]{XLSHXXF, XLSXXSSF};
    }
}

