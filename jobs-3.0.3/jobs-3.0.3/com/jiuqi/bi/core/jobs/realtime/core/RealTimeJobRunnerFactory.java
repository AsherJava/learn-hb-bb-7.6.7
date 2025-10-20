/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.core;

import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunner;

public class RealTimeJobRunnerFactory {
    private static RealTimeJobRunnerFactory factory = new RealTimeJobRunnerFactory();
    private RealTimeJobRunner runner;

    private RealTimeJobRunnerFactory() {
    }

    public static RealTimeJobRunnerFactory getInstance() {
        return factory;
    }

    public void setRunner(RealTimeJobRunner runner) {
        this.runner = runner;
    }

    public RealTimeJobRunner getDefaultRunner() {
        return this.runner;
    }
}

