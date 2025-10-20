/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.intf;

public interface ITaskProgressMonitor {
    public void progressAndLog(double var1, String var3);

    public void progressAndLogByStepSize(double var1, String var3);

    public StringBuilder getLogger();

    public String getLog();
}

