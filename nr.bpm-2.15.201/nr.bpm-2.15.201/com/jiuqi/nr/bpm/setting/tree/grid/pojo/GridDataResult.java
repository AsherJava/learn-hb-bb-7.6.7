/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.tree.grid.pojo;

import java.util.List;

public class GridDataResult {
    private String id;
    private String title;
    private boolean isStart;
    private String mark;
    private String startTime;
    private String updateTime;
    private String parentId;
    private List<GridDataResult> children;
    private boolean leafNode;
    private int startStatus;
    private int startType;
    private boolean _checked;

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

    public boolean isStart() {
        return this.isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public String getMark() {
        return this.mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isLeafNode() {
        return this.leafNode;
    }

    public void setLeafNode(boolean leafNode) {
        this.leafNode = leafNode;
    }

    public List<GridDataResult> getChildren() {
        return this.children;
    }

    public void setChildren(List<GridDataResult> children) {
        this.children = children;
    }

    public int getStartStatus() {
        return this.startStatus;
    }

    public void setStartStatus(int startStatus) {
        this.startStatus = startStatus;
    }

    public int getStartType() {
        return this.startType;
    }

    public void setStartType(int startType) {
        this.startType = startType;
    }

    public boolean is_checked() {
        return this._checked;
    }

    public void set_checked(boolean _checked) {
        this._checked = _checked;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

