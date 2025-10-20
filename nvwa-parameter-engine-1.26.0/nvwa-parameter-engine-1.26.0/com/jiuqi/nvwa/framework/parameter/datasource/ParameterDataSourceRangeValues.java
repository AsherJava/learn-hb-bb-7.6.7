/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

public final class ParameterDataSourceRangeValues {
    public String min;
    public String max;

    public ParameterDataSourceRangeValues() {
    }

    public ParameterDataSourceRangeValues(String min, String max) {
        this.min = min;
        this.max = max;
    }

    public boolean isNull() {
        return this.min == null && this.max == null;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("[");
        if (this.min == null) {
            buffer.append(' ');
        } else {
            buffer.append(this.min);
        }
        buffer.append(" - ");
        if (this.max == null) {
            buffer.append(' ');
        } else {
            buffer.append(this.max);
        }
        buffer.append(']');
        return buffer.toString();
    }
}

