/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface ICpoyErrorDesService {
    public String queryLastPeriod(JtableContext var1);

    public void copyErrorDesfromLastPeriod(JtableContext var1, AsyncTaskMonitor var2) throws Exception;

    public void copyErrorDesToOtherCurrency(JtableContext var1, AsyncTaskMonitor var2) throws Exception;
}

