/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.data.logic.facade.monitor;

import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;

public interface IFmlMonitor {
    public String getTaskId();

    public void progressAndMessage(double var1, String var3);

    public void error(String var1, Throwable var2);

    public void error(String var1, Throwable var2, Object var3);

    public void finish(String var1, Object var2);

    public void cancel(String var1, Object var2);

    public boolean isCancel();

    default public void canceled(String result, Object detail) {
    }

    default public AbstractMonitor getFmlEngineMonitor() {
        return null;
    }
}

