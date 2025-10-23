/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.soulution;

import java.util.Date;

public class SummarySolution {
    private String key;
    private String name;
    private String title;
    private String group;
    private String mainTask;
    private String targetDimension;
    private String order;
    private Date modifyTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMainTask() {
        return this.mainTask;
    }

    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

    public String getTargetDimension() {
        return this.targetDimension;
    }

    public void setTargetDimension(String targetDimension) {
        this.targetDimension = targetDimension;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

