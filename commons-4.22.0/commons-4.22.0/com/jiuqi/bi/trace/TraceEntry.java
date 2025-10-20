/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.trace;

import com.jiuqi.bi.trace.ResourceMonitorException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class TraceEntry
implements Cloneable {
    private final long createTime = System.currentTimeMillis();
    private final long threadId = Thread.currentThread().getId();
    private final String threadName = Thread.currentThread().getName();
    private Exception _bornStack = new Exception();
    private volatile List<StackTraceElement> _stackTrace;
    private static final int IGNORE_STACK_SIZE = 3;

    public long getCreateTime() {
        return this.createTime;
    }

    public long getThreadId() {
        return this.threadId;
    }

    public String getThreadName() {
        return this.threadName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<StackTraceElement> getStackTrace() {
        if (this._stackTrace == null) {
            TraceEntry traceEntry = this;
            synchronized (traceEntry) {
                if (this._stackTrace == null) {
                    StackTraceElement[] stacks = this._bornStack.getStackTrace();
                    if (stacks.length <= 3) {
                        this._stackTrace = Arrays.asList(stacks);
                    } else {
                        StackTraceElement[] shortStacks = new StackTraceElement[stacks.length - 3];
                        System.arraycopy(stacks, 3, shortStacks, 0, shortStacks.length);
                        this._stackTrace = Arrays.asList(shortStacks);
                    }
                    this._bornStack = null;
                }
            }
        }
        return this._stackTrace;
    }

    public void printTrace(PrintStream s, int ident) {
        this.ident(s, ident).println(this.toString());
        List<StackTraceElement> stacks = this.getStackTrace();
        for (StackTraceElement stack : stacks) {
            this.ident(s, ident).print("at ");
            s.println(stack);
        }
    }

    protected PrintStream ident(PrintStream s, int ident) {
        for (int i = 0; i < ident; ++i) {
            s.print('\t');
        }
        return s;
    }

    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss(SSS)");
        return format.format(new Date(this.createTime)) + " - " + this.threadName + "(" + this.threadId + ")";
    }

    public Object clone() {
        TraceEntry entry;
        try {
            entry = (TraceEntry)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new ResourceMonitorException(e);
        }
        entry._stackTrace = this.getStackTrace();
        entry._bornStack = null;
        return entry;
    }
}

