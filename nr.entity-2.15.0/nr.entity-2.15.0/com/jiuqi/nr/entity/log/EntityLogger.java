/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.log;

import com.jiuqi.nr.entity.log.LoggerAction;

public interface EntityLogger {
    public void info(String var1);

    public void info(String var1, Object ... var2);

    public void info(String var1, Throwable var2);

    public void debug(String var1);

    public void debug(String var1, Object ... var2);

    public void debug(String var1, Throwable var2);

    public void trace(String var1);

    public void trace(String var1, Object ... var2);

    public void trace(String var1, Throwable var2);

    public void error(String var1);

    public void error(String var1, Object ... var2);

    public void error(String var1, Throwable var2);

    public void accept(LoggerAction var1);

    public boolean isDebugEnabled();

    public boolean isTraceEnabled();
}

