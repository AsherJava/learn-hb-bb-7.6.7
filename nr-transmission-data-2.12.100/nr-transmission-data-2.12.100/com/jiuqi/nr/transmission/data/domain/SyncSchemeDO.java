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

public class SyncSchemeDO {
    private String key;
    private String code;
    private String title;
    private String group;
    private String desc;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updataTime;
    private String Order;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUpdataTime() {
        return this.updataTime;
    }

    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }

    public String getOrder() {
        return this.Order;
    }

    public void setOrder(String order) {
        this.Order = order;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<String, Object>(7);
        map.put("TS_KEY", this.getKey());
        map.put("TS_CODE", this.getCode());
        map.put("TS_TITLE", this.getTitle());
        map.put("TS_GROUP", this.getGroup());
        map.put("TS_DESC", this.getDesc());
        map.put("TS_UPDATE_TIME", this.getUpdataTime());
        map.put("TS_ORDER", this.getOrder());
        return map;
    }
}

