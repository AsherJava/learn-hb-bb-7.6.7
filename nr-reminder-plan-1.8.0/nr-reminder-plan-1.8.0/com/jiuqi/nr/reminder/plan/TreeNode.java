/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.reminder.plan;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

public class TreeNode {
    private String key;
    private String title;
    private boolean expand;
    private List<TreeNode> children;
    private String entityKey;
    private String period;
    private String fromPeriod;
    private String toPeriod;
    private String parentKey;
    private int type;
    private Boolean loading = false;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public Boolean getLoading() {
        return this.loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }
}

