/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.tree.pojo;

import com.jiuqi.nr.bpm.setting.tree.pojo.IFlowNode;
import java.util.ArrayList;
import java.util.List;

public class WorkflowTree<E extends IFlowNode> {
    private String key;
    private String title;
    private boolean expand;
    private String period;
    private boolean selected;
    private E data;
    private List<WorkflowTree<E>> children = new ArrayList<WorkflowTree<E>>();
    private boolean groupFlag;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public List<WorkflowTree<E>> getChildren() {
        return this.children;
    }

    public void setChildren(List<WorkflowTree<E>> children) {
        this.children = children;
    }

    public void appendChild(WorkflowTree<E> workflowdatas) {
        this.children.add(workflowdatas);
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isGroupFlag() {
        return this.groupFlag;
    }

    public void setGroupFlag(boolean groupFlag) {
        this.groupFlag = groupFlag;
    }
}

