/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.core;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;

public interface RealTimeJobRunner {
    public String commit(AbstractRealTimeJob var1) throws JobsException;

    public String commit(String var1, String var2, AbstractRealTimeJob var3) throws JobsException;

    public void cancel(String var1) throws JobsException;

    public void restartAll() throws JobsException;
}

