/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.text.spi;

public interface IFieldDataMonitor {
    public String getTaskId();

    public void progressAndMessage(double var1, String var3);

    public void error(String var1, Throwable var2);

    public void error(String var1, Throwable var2, Object var3);

    public void finish(String var1, Object var2);

    public void canceling(String var1, Object var2);

    public void canceled(String var1, Object var2);

    public boolean isCancel();
}

