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
import com.jiuqi.nr.table.io.ReadOptions;

public class StringParser
extends AbstractColumnParser<String> {
    public StringParser(ColumnType columnType) {
        super(columnType);
    }

    public StringParser(ColumnType columnType, ReadOptions readOptions) {
        super(columnType);
        if (readOptions.missingValueIndicators().length > 0) {
            this.missingValueStrings = Lists.newArrayList((Object[])readOptions.missingValueIndicators());
        }
    }

    @Override
    public boolean canParse(String s) {
        return true;
    }

    @Override
    public String parse(String s) {
        if (this.isMissing(s)) {
            return null;
        }
        return s;
    }
}

