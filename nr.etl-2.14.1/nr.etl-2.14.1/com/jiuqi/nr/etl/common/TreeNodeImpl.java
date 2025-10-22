/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.common;

import com.jiuqi.nr.etl.common.NodeEntites;
import java.util.List;

public class TreeNodeImpl {
    private String key;
    private String title;
    private boolean expand;
    private List<TreeNodeImpl> children;
    private String entityKey;
    private List<NodeEntites> entityList;
    private String period;
    private String taskId;

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

    public List<TreeNodeImpl> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNodeImpl> children) {
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

    public List<NodeEntites> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<NodeEntites> entityList) {
        this.entityList = entityList;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

