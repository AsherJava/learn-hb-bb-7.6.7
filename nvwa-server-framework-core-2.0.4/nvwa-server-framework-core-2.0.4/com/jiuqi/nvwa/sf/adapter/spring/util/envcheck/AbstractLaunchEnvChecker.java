/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.util.envcheck;

import java.util.concurrent.CountDownLatch;

public abstract class AbstractLaunchEnvChecker
implements Runnable {
    private CountDownLatch latch;

    @Override
    public void run() {
        this.doCheck();
        this.latch.countDown();
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public abstract void doCheck();

    public abstract String timeoutMessage();
}

