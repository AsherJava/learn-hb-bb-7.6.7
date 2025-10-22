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
import com.jiuqi.nr.table.data.type.IntColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.util.TypeUtils;

public class IntParser
extends AbstractColumnParser<Integer> {
    private final boolean ignoreZeroDecimal;

    public IntParser(ColumnType columnType) {
        super(columnType);
        this.ignoreZeroDecimal = true;
    }

    public IntParser(IntColumnType columnType, ReadOptions readOptions) {
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
            Integer.parseInt(AbstractColumnParser.remove(s, ','));
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Integer parse(String s) {
        return this.parseInt(s);
    }

    @Override
    public double parseDouble(String s) {
        return this.parseInt(s);
    }

    @Override
    public int parseInt(String str) {
        if (this.isMissing(str)) {
            return IntColumnType.missingValueIndicator();
        }
        String s = str;
        if (this.ignoreZeroDecimal) {
            s = TypeUtils.removeZeroDecimal(s);
        }
        return Integer.parseInt(AbstractColumnParser.remove(s, ','));
    }
}

