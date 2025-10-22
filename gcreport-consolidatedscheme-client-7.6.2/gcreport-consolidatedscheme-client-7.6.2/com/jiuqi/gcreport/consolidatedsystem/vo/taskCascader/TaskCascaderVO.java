/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.taskCascader;

import java.util.List;

public class TaskCascaderVO {
    private String value;
    private String label;
    private List<TaskCascaderVO> children;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TaskCascaderVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TaskCascaderVO> children) {
        this.children = children;
    }
}

