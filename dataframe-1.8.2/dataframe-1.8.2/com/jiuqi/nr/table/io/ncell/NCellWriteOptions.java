/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io.ncell;

import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.WriteOptions;

public class NCellWriteOptions
extends WriteOptions {
    private String name;
    private int[] columnWidth;

    public String getName() {
        return this.name;
    }

    public int[] getColumnWidth() {
        return this.columnWidth;
    }

    protected NCellWriteOptions(Builder builder) {
        super(builder);
    }

    public static Builder builder(Destination dest) {
        return new Builder(dest);
    }

    public static class Builder
    extends WriteOptions.Builder {
        public Builder(Destination dest) {
            super(dest);
        }

        public NCellWriteOptions build() {
            return new NCellWriteOptions(this);
        }
    }
}

