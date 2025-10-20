/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.bean;

import com.jiuqi.bi.core.jobs.bean.SchedulerState;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JobSchedulerStateBean {
    private String node;
    private String scheduler;
    private SchedulerState state;
    private long time;

    public String getNode() {
        return this.node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getScheduler() {
        return this.scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }

    public SchedulerState getState() {
        return this.state;
    }

    public void setState(SchedulerState state) {
        this.state = state;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("node", (Object)this.node);
        json.put("scheduler", (Object)this.scheduler);
        if (this.scheduler.indexOf("BI_JOB") != -1) {
            json.put("schedulerTitle", (Object)"\u4e3b\u4efb\u52a1\u8c03\u5ea6\u5668");
        } else if (this.scheduler.indexOf("BI_SUBJOB") != -1) {
            json.put("schedulerTitle", (Object)("\u5b50\u4efb\u52a1\u8c03\u5ea6\u5668\u3010" + this.scheduler.replace("BI_SUBJOB", "") + "\u3011"));
        } else {
            json.put("schedulerTitle", (Object)"\u4efb\u52a1\u8c03\u5ea6\u5668");
        }
        json.put("state", (Object)this.state);
        json.put("stateTitle", (Object)this.state.title());
        json.put("time", this.time);
        return json;
    }

    public static JSONArray list2Json(List<JobSchedulerStateBean> states) {
        JSONArray result = new JSONArray();
        for (JobSchedulerStateBean state : states) {
            result.put((Object)state.toJson());
        }
        return result;
    }
}

