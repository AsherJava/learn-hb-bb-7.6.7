/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.EnumItem;
import com.jiuqi.nr.table.io.ReadOptions;

public class EnumParser
extends AbstractColumnParser<EnumItem> {
    public EnumParser(ColumnType columnType) {
        super(columnType);
    }

    public EnumParser(ColumnType columnType, ReadOptions readOptions) {
        super(columnType);
    }

    @Override
    public boolean canParse(String str) {
        if (this.isMissing(str)) {
            return true;
        }
        return str.contains("|");
    }

    @Override
    public EnumItem parse(String str) {
        if (null != str) {
            String[] split = str.split("\\|");
            EnumItem ei = new EnumItem(split[0], split[1]);
            return ei;
        }
        return null;
    }
}

