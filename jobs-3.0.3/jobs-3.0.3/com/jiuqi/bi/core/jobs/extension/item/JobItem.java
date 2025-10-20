/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension.item;

public class JobItem {
    private String jobId;
    private String jobTitle;
    private String folderGuid;

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return this.jobId;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFolderGuid() {
        return this.folderGuid;
    }

    public void setFolderGuid(String folderGuid) {
        this.folderGuid = folderGuid;
    }
}

