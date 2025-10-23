/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.transmission.data.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncEntityLastHistoryDO {
    private static final Logger log = LoggerFactory.getLogger(SyncEntityLastHistoryDO.class);
    private String key;
    private String taskKey;
    private String period;
    private String formKey;
    private String entity;
    private String userId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date time;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("TEH_KEY", this.getKey());
        map.put("TEH_TASK", this.getTaskKey());
        map.put("TEH_PERIOD", this.getPeriod());
        map.put("TEH_FORM", this.getFormKey());
        map.put("TEH_ENTITY", this.getEntity());
        map.put("TEH_USERID", this.getUserId());
        map.put("TEH_TIME", this.getTime());
        return map;
    }
}

