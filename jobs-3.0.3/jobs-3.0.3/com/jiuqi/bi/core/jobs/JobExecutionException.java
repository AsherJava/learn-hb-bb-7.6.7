/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.JobsException;

public class JobExecutionException
extends JobsException {
    private static final long serialVersionUID = 8842941720773831801L;

    public JobExecutionException(String message) {
        super(message);
    }

    public JobExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobExecutionException(Throwable cause) {
        super(cause);
    }
}

