/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;

public class EmptyFmlMonitor
implements IFmlMonitor {
    @Override
    public String getTaskId() {
        return null;
    }

    @Override
    public void progressAndMessage(double currProgress, String message) {
    }

    @Override
    public void error(String message, Throwable sender) {
    }

    @Override
    public void error(String message, Throwable sender, Object detail) {
    }

    @Override
    public void finish(String result, Object detail) {
    }

    @Override
    public void cancel(String message, Object detail) {
    }

    @Override
    public boolean isCancel() {
        return false;
    }

    public static EmptyFmlMonitor getInstance() {
        return EmptyFmlMonitorInstance.INSTANCE;
    }

    private static class EmptyFmlMonitorInstance {
        private static final EmptyFmlMonitor INSTANCE = new EmptyFmlMonitor();

        private EmptyFmlMonitorInstance() {
        }
    }
}

