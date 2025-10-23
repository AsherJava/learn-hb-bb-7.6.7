/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.report;

import com.jiuqi.nr.summary.model.report.PublishState;
import java.util.Date;

public class SummaryReport {
    private String key;
    private String name;
    private String title;
    private String solution;
    private PublishState state;
    private Date modifyTime;
    private String order;

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

    public String getSolution() {
        return this.solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public PublishState getState() {
        return this.state;
    }

    public void setState(PublishState state) {
        this.state = state;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

