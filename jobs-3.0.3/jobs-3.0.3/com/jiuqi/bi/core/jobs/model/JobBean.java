/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.model;

public class JobBean
implements Cloneable {
    private String jobId;
    private String jobTitle;
    private String categoryId;
    private String folderGuid;

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFolderGuid() {
        return this.folderGuid;
    }

    public void setFolderGuid(String folderGuid) {
        this.folderGuid = folderGuid;
    }

    public JobBean clone() {
        try {
            return (JobBean)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

