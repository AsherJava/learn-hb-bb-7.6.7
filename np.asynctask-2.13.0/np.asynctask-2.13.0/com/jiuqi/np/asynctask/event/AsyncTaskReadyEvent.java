/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.event;

import org.springframework.context.ApplicationEvent;

public class AsyncTaskReadyEvent
extends ApplicationEvent {
    private String serveCode;

    public AsyncTaskReadyEvent() {
        super(0);
    }

    public String getServeCode() {
        return this.serveCode;
    }

    public void setServeCode(String serveCode) {
        this.serveCode = serveCode;
    }
}

