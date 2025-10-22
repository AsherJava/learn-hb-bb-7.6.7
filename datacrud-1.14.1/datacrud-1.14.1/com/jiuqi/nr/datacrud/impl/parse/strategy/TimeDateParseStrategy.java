/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.nr.datacrud.common.DateUtils;
import com.jiuqi.nr.datacrud.impl.parse.strategy.BaseDateParseStrategy;
import java.text.ParseException;
import java.util.Date;

public class TimeDateParseStrategy
extends BaseDateParseStrategy {
    @Override
    protected String formatStr() {
        return "HH:mm:ss";
    }

    @Override
    protected int getDataType() {
        return 8;
    }

    @Override
    protected Date parseDate(String toString) throws ParseException {
        return DateUtils.timeToDate(toString);
    }
}

