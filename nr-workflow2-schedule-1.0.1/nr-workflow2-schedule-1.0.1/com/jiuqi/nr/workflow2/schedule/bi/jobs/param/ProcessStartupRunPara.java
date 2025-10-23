/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.workflow2.service.para.ProcessJsonDataDeserializer
 *  com.jiuqi.nr.workflow2.service.para.ProcessJsonDataSerializer
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.param.IProcessStartupRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessJsonDataDeserializer;
import com.jiuqi.nr.workflow2.service.para.ProcessJsonDataSerializer;
import org.json.JSONObject;

public class ProcessStartupRunPara
implements IProcessStartupRunPara {
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String taskKey;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String period;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private JSONObject envVariables;

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    @JsonSerialize(using=ProcessJsonDataSerializer.class)
    public JSONObject getEnvVariables() {
        return this.envVariables;
    }

    @JsonDeserialize(using=ProcessJsonDataDeserializer.class)
    public void setEnvVariables(JSONObject envVariables) {
        this.envVariables = envVariables;
    }
}

