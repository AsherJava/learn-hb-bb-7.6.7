/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.oss;

import com.jiuqi.bi.core.jobs.oss.AbstractJobResult;

public class JobFileResult
extends AbstractJobResult {
    private String filePath;

    public JobFileResult(String name, String filePath) {
        this.filePath = filePath;
        this.setName(name);
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

