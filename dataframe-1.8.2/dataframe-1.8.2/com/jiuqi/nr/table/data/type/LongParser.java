/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package com.jiuqi.nr.table.data.type;

import com.google.common.collect.Lists;
import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.type.LongColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.util.TypeUtils;

public class LongParser
extends AbstractColumnParser<Long> {
    private final boolean ignoreZeroDecimal;

    public LongParser(ColumnType columnType) {
        super(columnType);
        this.ignoreZeroDecimal = true;
    }

    public LongParser(LongColumnType columnType, ReadOptions readOptions) {
        super(columnType);
        if (readOptions.missingValueIndicators().length > 0) {
            this.missingValueStrings = Lists.newArrayList((Object[])readOptions.missingValueIndicators());
        }
        this.ignoreZeroDecimal = readOptions.ignoreZeroDecimal();
    }

    @Override
    public boolean canParse(String str) {
        if (this.isMissing(str)) {
            return true;
        }
        String s = str;
        try {
            if (this.ignoreZeroDecimal) {
                s = TypeUtils.removeZeroDecimal(s);
            }
            Long.parseLong(AbstractColumnParser.remove(s, ','));
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Long parse(String s) {
        if (this.isMissing(s)) {
            return null;
        }
        return this.parseLong(s);
    }

    @Override
    public double parseDouble(String str) {
        return this.parseLong(str);
    }

    @Override
    public long parseLong(String str) {
        if (this.isMissing(str)) {
            return LongColumnType.missingValueIndicator();
        }
        String s = str;
        if (this.ignoreZeroDecimal) {
            s = TypeUtils.removeZeroDecimal(s);
        }
        return Long.parseLong(AbstractColumnParser.remove(s, ','));
    }
}

