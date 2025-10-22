/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 */
package com.jiuqi.nr.table.data.type;

import com.google.common.base.Objects;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.df.Options;
import java.text.Format;

public abstract class AbstractColumnType
implements ColumnType {
    private final int byteSize;
    private final String name;

    protected AbstractColumnType(int byteSize, String name) {
        this.byteSize = byteSize;
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int byteSize() {
        return this.byteSize;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AbstractColumnType that = (AbstractColumnType)o;
        return this.byteSize == that.byteSize && Objects.equal((Object)this.name, (Object)that.name);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.byteSize, this.name});
    }

    public abstract DataType type();

    public Format format(Options options) {
        return null;
    }
}

