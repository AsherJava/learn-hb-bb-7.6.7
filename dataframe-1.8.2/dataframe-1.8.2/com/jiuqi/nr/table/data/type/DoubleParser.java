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
import com.jiuqi.nr.table.data.type.DoubleColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.text.NumberFormat;
import java.text.ParseException;

public class DoubleParser
extends AbstractColumnParser<Double> {
    public DoubleParser(ColumnType columnType) {
        super(columnType);
    }

    public DoubleParser(DoubleColumnType doubleColumnType, ReadOptions readOptions) {
        super(doubleColumnType);
        if (readOptions.missingValueIndicators().length > 0) {
            this.missingValueStrings = Lists.newArrayList((Object[])readOptions.missingValueIndicators());
        }
    }

    @Override
    public boolean canParse(String s) {
        if (this.isMissing(s)) {
            return true;
        }
        try {
            if (this.isPercent(AbstractColumnParser.remove(s, ','))) {
                s = AbstractColumnParser.remove(s, ',');
                NumberFormat.getPercentInstance().parse(s);
            } else {
                Double.parseDouble(AbstractColumnParser.remove(s, ','));
            }
        }
        catch (IndexOutOfBoundsException | NumberFormatException | ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public Double parse(String s) {
        return this.parseDouble(s);
    }

    @Override
    public double parseDouble(String s) {
        if (this.isMissing(s)) {
            return DoubleColumnType.missingValueIndicator();
        }
        if (this.isPercent(AbstractColumnParser.remove(s, ','))) {
            s = AbstractColumnParser.remove(s, ',').substring(0, s.length() - 1);
            return Double.parseDouble(s) / 100.0;
        }
        return Double.parseDouble(AbstractColumnParser.remove(s, ','));
    }

    private boolean isPercent(String s) {
        return s.charAt(s.length() - 1) == '%';
    }
}

