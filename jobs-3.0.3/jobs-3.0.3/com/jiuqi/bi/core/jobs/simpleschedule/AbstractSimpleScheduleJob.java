/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.simpleschedule;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.simpleschedule.bean.SimpleJobExecuteType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSimpleScheduleJob
implements Serializable {
    private String title;
    private Map<String, String> params = new HashMap<String, String>();
    private String userName;
    private String userGuid;
    private String jobId;
    private String jobGroupId;
    private String jobGroupTitle;
    private SimpleJobExecuteType executeType;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params == null ? new HashMap<String, String>() : params;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public abstract void execute(JobContext var1) throws JobExecutionException;

    public String getJobGroupId() {
        return this.jobGroupId;
    }

    public String getJobGroupTitle() {
        return this.jobGroupTitle;
    }

    public void setJobGroupId(String jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public void setJobGroupTitle(String jobGroupTitle) {
        this.jobGroupTitle = jobGroupTitle;
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public SimpleJobExecuteType getExecuteType() {
        return this.executeType;
    }

    public void setExecuteType(SimpleJobExecuteType executeType) {
        this.executeType = executeType;
    }
}

