/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.trace;

import com.jiuqi.bi.trace.TraceEntry;
import java.io.PrintStream;
import java.util.List;

public class ResourceAction
extends TraceEntry {
    private final String action;
    private long refCount = 1L;

    public ResourceAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public long getRefCount() {
        return this.refCount;
    }

    public long addRefCount() {
        ++this.refCount;
        return this.refCount;
    }

    @Override
    public String toString() {
        return String.format("%s: %d invokes, 1st at %s", this.action, this.refCount, super.toString());
    }

    public void printTrace(PrintStream s, int num, int ident) {
        this.ident(s, ident).printf("%d.%s%n", num, this.toString());
        List<StackTraceElement> stacks = this.getStackTrace();
        for (StackTraceElement stack : stacks) {
            this.ident(s, ident).print("at ");
            s.println(stack);
        }
    }
}

