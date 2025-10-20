/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.TimeCalcException;
import java.util.Calendar;

public interface ITimeReader {
    public Calendar getTimeKeyDate();

    public String getTimeKey() throws TimeCalcException;

    public Object getValue(String var1) throws TimeCalcException;

    public Object getValue(int var1) throws TimeCalcException;

    public Calendar getLastDate() throws TimeCalcException;

    public String getLastDay() throws TimeCalcException;

    public int getDays() throws TimeCalcException;
}

