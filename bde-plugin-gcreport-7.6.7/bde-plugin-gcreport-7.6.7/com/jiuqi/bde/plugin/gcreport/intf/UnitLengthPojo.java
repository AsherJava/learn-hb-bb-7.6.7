/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.gcreport.intf;

public class UnitLengthPojo {
    private boolean variableLength;
    private int length;

    public boolean isVariableLength() {
        return this.variableLength;
    }

    public void setVariableLength(boolean variableLength) {
        this.variableLength = variableLength;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String toString() {
        return "UnitLengthPojo{variableLength=" + this.variableLength + ", length=" + this.length + '}';
    }
}

