/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.webserviceclient.vo;

import java.util.List;

public class DataIntegrationTaskTreeVo {
    private String id;
    private String label;
    private String parentId;
    private boolean leafFlag;
    private String taskDescription;
    private List<DataIntegrationTaskTreeVo> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public List<DataIntegrationTaskTreeVo> getChildren() {
        return this.children;
    }

    public void setChildren(List<DataIntegrationTaskTreeVo> children) {
        this.children = children;
    }
}

