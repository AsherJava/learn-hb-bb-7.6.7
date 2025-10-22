/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.time.setting.bean;

import java.io.Serializable;
import java.util.List;

public class TimeSettingResult
implements Serializable {
    private static final long serialVersionUID = 4523218808688905938L;
    private String id;
    private String unitName;
    private String submitStartTime;
    private String deadLineTime;
    private String repayDeadline;
    private List<TimeSettingResult> children;
    private boolean expanded;
    private boolean leafNode;
    private String parentId;
    private boolean defaultColor;

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<TimeSettingResult> getChildren() {
        return this.children;
    }

    public void setChildren(List<TimeSettingResult> children) {
        this.children = children;
    }

    public String getSubmitStartTime() {
        return this.submitStartTime;
    }

    public void setSubmitStartTime(String submitStartTime) {
        this.submitStartTime = submitStartTime;
    }

    public String getDeadLineTime() {
        return this.deadLineTime;
    }

    public void setDeadLineTime(String deadLineTime) {
        this.deadLineTime = deadLineTime;
    }

    public String getRepayDeadline() {
        return this.repayDeadline;
    }

    public void setRepayDeadline(String repayDeadline) {
        this.repayDeadline = repayDeadline;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLeafNode() {
        return this.leafNode;
    }

    public void setLeafNode(boolean leafNode) {
        this.leafNode = leafNode;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isDefaultColor() {
        return this.defaultColor;
    }

    public void setDefaultColor(boolean defaultColor) {
        this.defaultColor = defaultColor;
    }
}

