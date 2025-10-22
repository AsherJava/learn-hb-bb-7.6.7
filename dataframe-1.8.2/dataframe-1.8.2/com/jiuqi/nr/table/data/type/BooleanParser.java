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
import com.jiuqi.nr.table.data.type.BooleanColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.util.Arrays;
import java.util.List;

public class BooleanParser
extends AbstractColumnParser<Boolean> {
    private static final List<String> FALSE_STRINGS_FOR_DETECTION = Arrays.asList("F", "f", "N", "n", "FALSE", "false", "False");
    private static final List<String> TRUE_STRINGS_FOR_DETECTION = Arrays.asList("T", "t", "Y", "y", "TRUE", "true", "True");
    private static final List<String> TRUE_STRINGS = Arrays.asList("T", "t", "Y", "y", "TRUE", "true", "True", "1");
    private static final List<String> FALSE_STRINGS = Arrays.asList("F", "f", "N", "n", "FALSE", "false", "False", "0");

    public BooleanParser(ColumnType columnType) {
        super(columnType);
    }

    public BooleanParser(BooleanColumnType booleanColumnType, ReadOptions readOptions) {
        super(booleanColumnType);
        if (readOptions.missingValueIndicators().length > 0) {
            this.missingValueStrings = Lists.newArrayList((Object[])readOptions.missingValueIndicators());
        }
    }

    @Override
    public boolean canParse(String s) {
        if (this.isMissing(s)) {
            return true;
        }
        return TRUE_STRINGS_FOR_DETECTION.contains(s) || FALSE_STRINGS_FOR_DETECTION.contains(s);
    }

    @Override
    public Boolean parse(String s) {
        if (this.isMissing(s)) {
            return null;
        }
        if (TRUE_STRINGS.contains(s)) {
            return true;
        }
        if (FALSE_STRINGS.contains(s)) {
            return false;
        }
        throw new IllegalArgumentException("Attempting to convert non-boolean value " + s + " to Boolean");
    }

    @Override
    public byte parseByte(String s) {
        if (this.isMissing(s)) {
            return BooleanColumnType.MISSING_VALUE;
        }
        if (TRUE_STRINGS.contains(s)) {
            return 1;
        }
        if (FALSE_STRINGS.contains(s)) {
            return 0;
        }
        throw new IllegalArgumentException("Attempting to convert non-boolean value " + s + " to Boolean");
    }
}

