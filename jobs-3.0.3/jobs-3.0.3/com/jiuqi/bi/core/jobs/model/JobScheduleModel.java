/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.model;

import com.jiuqi.bi.core.jobs.model.JobModel;
import org.json.JSONException;
import org.json.JSONObject;

public class JobScheduleModel
extends JobModel {
    private long lastExecuteTime;
    private long nextExecuteTime;

    public long getLastExecuteTime() {
        return this.lastExecuteTime;
    }

    public void setLastExecuteTime(long lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public long getNextExecuteTime() {
        return this.nextExecuteTime;
    }

    public void setNextExecuteTime(long nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    @Override
    public void toJson(JSONObject json) throws JSONException {
        super.toJson(json);
        json.put("lastExecuteTime", this.getLastExecuteTime());
        json.put("nextExecuteTime", this.getNextExecuteTime());
    }
}

