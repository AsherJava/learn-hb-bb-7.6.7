/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.util.TypeUtils;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractColumnParser<T>
implements Function<String, T> {
    private final ColumnType columnType;
    protected List<String> missingValueStrings = TypeUtils.MISSING_INDICATORS;

    public AbstractColumnParser(ColumnType columnType) {
        this.columnType = columnType;
    }

    public abstract boolean canParse(String var1);

    public abstract T parse(String var1);

    public ColumnType columnType() {
        return this.columnType;
    }

    public boolean isMissing(String s) {
        if (s == null) {
            return true;
        }
        return s.isEmpty() || this.missingValueStrings.contains(s);
    }

    public byte parseByte(String s) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't support parsing to booleans");
    }

    public int parseInt(String s) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't support parsing to ints");
    }

    public short parseShort(String s) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't support parsing to shorts");
    }

    public long parseLong(String s) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't support parsing to longs");
    }

    public double parseDouble(String s) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't support parsing to doubles");
    }

    public float parseFloat(String s) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't support parsing to floats");
    }

    protected static String remove(String str, char remove) {
        if (str == null || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == remove) continue;
            chars[pos++] = chars[i];
        }
        return new String(chars, 0, pos);
    }

    public void setMissingValueStrings(List<String> missingValueStrings) {
        this.missingValueStrings = missingValueStrings;
    }

    @Override
    public T apply(String t) {
        return this.parse(t);
    }
}

