/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.nr.datacrud.common.DateUtils;
import com.jiuqi.nr.datacrud.impl.parse.strategy.BaseDateParseStrategy;
import java.util.Date;

public class DateParseStrategy
extends BaseDateParseStrategy {
    @Override
    protected Date parseDate(String toString) {
        return DateUtils.stringToDate(toString);
    }

    @Override
    protected String formatStr() {
        return "yyyy-MM-dd";
    }

    @Override
    protected int getDataType() {
        return 5;
    }
}

