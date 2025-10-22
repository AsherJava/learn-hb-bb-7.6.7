/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.period.service;

import com.jiuqi.np.common.exception.JQException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface PeriodIOService {
    public void importPeriod(InputStream var1) throws JQException;

    public void importPeriodData(InputStream var1) throws JQException;

    public void exportPeriods(List<String> var1, OutputStream var2) throws JQException;

    public void exportPeriod(String var1, OutputStream var2) throws JQException;

    public void exportPeriodData(String var1, OutputStream var2) throws JQException;
}

