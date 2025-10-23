/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.workflow2.instance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.workflow2.instance.entity.InstanceFormDetailData;
import java.util.Calendar;
import java.util.List;

public class InstanceFormDetailDataImpl
implements InstanceFormDetailData {
    private String key;
    private String code;
    private String title;
    private int state;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Calendar startTime;
    private List<InstanceFormDetailData> children;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public Calendar getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    @Override
    public List<InstanceFormDetailData> getChildren() {
        return this.children;
    }

    public void setChildren(List<InstanceFormDetailData> children) {
        this.children = children;
    }
}

