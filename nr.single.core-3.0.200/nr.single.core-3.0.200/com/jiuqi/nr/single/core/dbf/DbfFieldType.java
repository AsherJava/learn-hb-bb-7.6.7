/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.dbf;

public class DbfFieldType {
    public static final char FDT_STRING = 'C';
    public static final char FDT_NUMERIC = 'N';
    public static final char FDT_FLOAT = 'F';
    public static final char FDT_BOOLEAN = 'L';
    public static final char FDT_DATE = 'D';
    public static final char FDT_INTEGER = 'I';
    public static final char FDT_DOUBLE = 'B';
    public static final char FDT_CURRENCY = 'Y';
    public static final char FDT_DATETIME = 'T';
    public static final char FDT_MEMO = 'M';
    public static final char FDT_BLOB = 'G';
    public static final char FDT_GRAPH = 'P';
    public static final char FDT_TEXT = 'R';
    public static final char FDT_FILE = 'O';

    private DbfFieldType() {
        throw new IllegalStateException("Utility class");
    }
}

