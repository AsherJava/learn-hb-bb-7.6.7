/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension;

import java.io.Serializable;

public class JobListenerContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String jobGuid;
    private String optionOrigin;

    public JobListenerContext(String jobGuid, String optionOrigin) {
        this.jobGuid = jobGuid;
        this.optionOrigin = optionOrigin;
    }

    public String getJobGuid() {
        return this.jobGuid;
    }

    public String getOptionOrigin() {
        return this.optionOrigin;
    }
}

