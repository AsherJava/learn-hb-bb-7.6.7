/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Calendar;

public abstract class ExecutorOfWeek
implements ITimeExecutor {
    private int firstDayOfWeek;

    @Override
    public void set(TimeFieldInfo field, Object value) throws TimeCalcException {
        ITimeExecutor.super.set(field, value);
        if (value != null && field.isTimeKey()) {
            Calendar date = (Calendar)field.getValue();
            if (this.firstDayOfWeek <= 0) {
                this.firstDayOfWeek = date.get(7);
            } else {
                date.setFirstDayOfWeek(this.firstDayOfWeek);
            }
        }
    }

    @Override
    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek <= 0 ? 2 : this.firstDayOfWeek;
    }

    @Override
    public void setFirstDayOfWeek(int firstDayOfWeek) {
        if (firstDayOfWeek < 1 || firstDayOfWeek > 7) {
            throw new IllegalArgumentException("\u975e\u6cd5\u7684\u5468\u8d77\u59cb\u65e5\uff1a" + firstDayOfWeek);
        }
        this.firstDayOfWeek = firstDayOfWeek;
    }
}

