/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.serializer.SchemeGroupDeserializer;
import com.jiuqi.nr.batch.summary.storage.entity.serializer.SchemeGroupSerializer;
import java.util.Date;

@JsonSerialize(using=SchemeGroupSerializer.class)
@JsonDeserialize(using=SchemeGroupDeserializer.class)
public class SummaryGroupDefine
implements SummaryGroup {
    private String key;
    private String title;
    private String parent;
    private String task;
    private Date updateTime;
    private String creator;
    private String ordinal;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }
}

