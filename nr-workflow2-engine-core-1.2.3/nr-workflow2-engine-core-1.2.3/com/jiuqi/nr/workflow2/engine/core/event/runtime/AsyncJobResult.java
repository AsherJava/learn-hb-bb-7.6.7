/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.monitor.JobResult
 */
package com.jiuqi.nr.workflow2.engine.core.event.runtime;

import com.jiuqi.bi.core.jobs.monitor.JobResult;

public enum AsyncJobResult {
    UNFINISHED(1, JobResult.getResultTitle((int)1)),
    CANCELED(2, JobResult.getResultTitle((int)2)),
    TERMINATED(3, JobResult.getResultTitle((int)3)),
    EXCEPTION(4, JobResult.getResultTitle((int)4)),
    TIMEOUT(5, "\u8d85\u65f6\u672a\u6267\u884c"),
    SUCCESS(100, JobResult.getResultTitle((int)100)),
    FAILURE(-100, JobResult.getResultTitle((int)-100)),
    UNCERTIFICATED(-9999, JobResult.getResultTitle((int)-9999));

    public final int value;
    public final String name;

    private AsyncJobResult(int value, String name) {
        this.value = value;
        this.name = name;
    }
}

