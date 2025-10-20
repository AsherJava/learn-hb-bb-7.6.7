/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Calendar;

interface ITimeExecutor {
    public boolean isNull(boolean var1);

    public boolean isNull(int var1);

    public Calendar exec(boolean var1) throws TimeCalcException;

    default public void set(TimeFieldInfo field, Object value) throws TimeCalcException {
        field.setValue(value);
    }

    default public int getFirstDayOfWeek() {
        return 2;
    }

    default public void setFirstDayOfWeek(int firstDayOfWeek) {
    }

    default public int getFiscalMinMonth() {
        return -1;
    }

    default public int getFiscalMaxMonth() {
        return -1;
    }

    default public void setFiscalMonth(int minMonth, int maxMonth) {
    }
}

