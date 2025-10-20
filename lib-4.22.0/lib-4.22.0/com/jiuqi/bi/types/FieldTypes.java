/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.types;

public class FieldTypes {
    public static final int UNKNOWN = -1;
    public static final int GENERAL_DIM = 1;
    public static final int TIME_DIM = 2;
    public static final int MEASURE = 3;
    public static final int DESCRIPTION = 4;

    private FieldTypes() {
    }

    public static String toString(int type) {
        switch (type) {
            case 1: {
                return "GENERAL_DIM";
            }
            case 2: {
                return "TIME_DIM";
            }
            case 3: {
                return "MEASURE";
            }
            case 4: {
                return "DESCRIPTION";
            }
        }
        return "UNKNOWN";
    }
}

