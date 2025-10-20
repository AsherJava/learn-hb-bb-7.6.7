/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.JobExecutionException;

public class JobOutOfThreadException
extends JobExecutionException {
    private static final long serialVersionUID = 8842941720773831801L;

    public JobOutOfThreadException(String message) {
        super(message);
    }

    public JobOutOfThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobOutOfThreadException(Throwable cause) {
        super(cause);
    }
}

