/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.bean;

import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.model.JobModel;
import org.json.JSONException;
import org.json.JSONObject;

public class JobInfo {
    private JobModel jobModel;
    private JobInstanceBean instance;

    public JobInfo(JobModel jobModel, JobInstanceBean instance) {
        this.jobModel = jobModel;
        this.instance = instance;
    }

    public JobModel getJobModel() {
        return this.jobModel;
    }

    public void setJobModel(JobModel jobModel) {
        this.jobModel = jobModel;
    }

    public JobInstanceBean getInstance() {
        return this.instance;
    }

    public void setInstance(JobInstanceBean instance) {
        this.instance = instance;
    }

    public void toJson(JSONObject json) throws JSONException {
        if (json == null) {
            json = new JSONObject();
        }
        if (this.jobModel != null) {
            this.jobModel.toJson(json);
        }
        if (this.instance != null) {
            JSONObject instanceJson = new JSONObject();
            this.instance.toJson(instanceJson);
            json.put("instance", (Object)instanceJson);
        }
    }
}

