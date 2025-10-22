/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.task.vo;

public class TaskOrgDataVO {
    private String id;
    private String title;

    public TaskOrgDataVO() {
    }

    public TaskOrgDataVO(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

