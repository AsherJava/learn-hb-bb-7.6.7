/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.param;

import org.json.JSONObject;

public interface IProcessStartupRunPara {
    public String getTaskKey();

    public String getPeriod();

    public JSONObject getEnvVariables();
}

