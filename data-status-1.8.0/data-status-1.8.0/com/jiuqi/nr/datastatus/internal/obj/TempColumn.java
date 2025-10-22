/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datastatus.internal.obj;

public class TempColumn {
    private String name;
    private int precision;
    private int dataType;

    public TempColumn(String name, int precision, int dataType) {
        this.name = name;
        this.precision = precision;
        this.dataType = dataType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}

