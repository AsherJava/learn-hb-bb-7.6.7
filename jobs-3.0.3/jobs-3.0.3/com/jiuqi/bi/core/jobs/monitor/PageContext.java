/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.monitor;

public class PageContext {
    private int start;
    private int end;

    public PageContext(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }
}

