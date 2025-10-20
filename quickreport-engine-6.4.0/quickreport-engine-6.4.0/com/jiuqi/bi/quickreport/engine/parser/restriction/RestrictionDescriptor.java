/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;

public class RestrictionDescriptor {
    public static final int MODE_ALL = 1;
    public static final int MODE_OFFSET = 2;
    public static final int MODE_VALUE = 3;
    public static final int MODE_VALUES = 4;
    public static final int MODE_EXPRESSION = 5;
    public static final int MODE_MAPPING = 6;
    private int mode;
    private DSFieldInfo field;
    private Object info;

    public RestrictionDescriptor(int mode, DSFieldInfo field, Object info) {
        this.mode = mode;
        this.field = field;
        this.info = info;
    }

    public int getMode() {
        return this.mode;
    }

    public DSFieldInfo getField() {
        return this.field;
    }

    public Object getInfo() {
        return this.info;
    }

    public boolean restrictOn(String dataSetName) {
        String curDSName = this.field == null ? null : this.field.dataSetName;
        return curDSName != null && curDSName.equalsIgnoreCase(dataSetName);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.field).append(':');
        if (this.mode == 1) {
            buffer.append("ALL");
        } else {
            buffer.append(this.info);
        }
        return buffer.toString();
    }
}

