/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.nr.datacrud.common.DateUtils;
import com.jiuqi.nr.datacrud.impl.parse.strategy.BaseDateParseStrategy;
import java.util.Date;

public class DateTimeParseStrategy
extends BaseDateParseStrategy {
    @Override
    protected String formatStr() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    @Override
    protected int getDataType() {
        return 2;
    }

    @Override
    protected Date parseDate(String toString) {
        return DateUtils.stringTimeToDate(toString);
    }
}

