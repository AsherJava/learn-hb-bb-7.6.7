/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.monitor;

import com.jiuqi.bi.monitor.AbstractProgressMonitor;

public final class ProgressMonitor
extends AbstractProgressMonitor {
    private String promptMessage;

    @Override
    protected void notify(double newPosition) {
    }

    @Override
    public void prompt(String message) {
        this.promptMessage = message;
    }

    public String getLastPrompt() {
        return this.promptMessage;
    }
}

