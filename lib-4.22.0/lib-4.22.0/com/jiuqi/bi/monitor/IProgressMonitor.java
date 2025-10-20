/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.monitor;

import com.jiuqi.bi.monitor.ProgressException;

public interface IProgressMonitor {
    public void prompt(String var1);

    public void startTask(String var1, int var2);

    public void startTask(String var1, int[] var2);

    public void finishTask();

    public void finishTask(String var1) throws ProgressException;

    public boolean isCanceled();

    public void cancel();

    public void stepIn();

    public String getCurrentTask();

    public int getCurrentLevel();

    public double getPosition();
}

